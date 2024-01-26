package com.wonders.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.wonders.dao.CommonMapper;
import com.wonders.dao.StudentMapper;
import com.wonders.dynamic.DataSourceInfo;
import com.wonders.dynamic.DataSourceUtils;
import com.wonders.dynamic.DbsConstant;
import com.wonders.dynamic.DynamicDataSourceHolder;
import com.wonders.entity.Student;
import com.wonders.service.StudentService;
import com.wonders.service.StudentServiceImpl;
import com.wonders.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description: TODO
 * @Author: George
 * @CreateDate: 2024/1/22 15:02
 * @Version: V1.0
 */
@Slf4j
@Api(tags="动态切换多数据源测试")
@RestController
public class TestController {
    @Value("${terrain.sqlite.directory}")
    private String terrainSqliteDirectory;
    @Autowired
    private UserService userService;
    @Resource
    DataSourceUtils dataSourceUtils;
    //@Autowired
    //private StudentMapper studentMapper;
    @Autowired
    private CommonMapper commonMapper;
    //@Autowired
    //private StudentServiceImpl studentService;  //一定要注册实现层才可以

    @RequestMapping("/asycu")
    public String getAsycUser() throws ExecutionException, InterruptedException {
        //调用异步方法返回类型为Future<>
        Future<String> future = userService.getAsycUser();

        //get方法得到返回的结果
        return future.get();
    }
    @ApiOperation(value="方法一：方法里设置数据源", notes="test")
    @GetMapping(value="/terrain/{z}/{x}/{y}.terrain",produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Object getTerrain(Integer z, Integer x, Integer y){
        //如果 z < 10，那么表名是 “blocks“。
        //否则 表名 = “blocks_” + tostring(z) + “_” + tostring(x / 512) + “_” + tostring(y / 512)
        if(z < 0 || z > 15 ){
            return null;
        }
        String tableName = "";

        if(z < 10){
            tableName = "blocks";
        }else{
            tableName = "blocks_"+String.valueOf(z)+"_"+String.valueOf(x/512)+"_"+String.valueOf(y/512);
        }
        String sql = "select tile from "+tableName+" where z="+z+" and x="+x+" and y="+y+" limit 1";
        log.debug("sql:"+sql);

        try{
            DynamicDataSourceHolder.setDynamicDataSourceKey("sqlite01");
            //byte[] res = (byte[])jdbcTemplate.queryForObject(sql, Object.class);
            Object res = null;
            res = commonMapper.querySql(sql);
            //log.debug("res.length:"+res.length);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    @ApiOperation(value="方法二：动态传递sqlite类型，动态切换多数据源", notes="test")
    @GetMapping(value="/terrain/{type}/{z}/{x}/{y}.terrain",produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Object getTerrainType(@PathVariable String type,@PathVariable Integer z, @PathVariable Integer x, @PathVariable Integer y){
        String tableName = "";
        Object res = null;
        if(z < 10){
            tableName = "blocks";
        }else{
            tableName = "blocks_"+String.valueOf(z)+"_"+String.valueOf(x/512)+"_"+String.valueOf(y/512);
        }
        String sql = "select tile from "+tableName+" where z="+z+" and x="+x+" and y="+y+" limit 1";
        log.debug("sql:"+sql);


        //3、从数据库获取连接信息，然后获取数据
        //模拟从数据库中获取的连接
        DataSourceInfo dataSourceInfo = new DataSourceInfo(
                "jdbc:sqlite:"+terrainSqliteDirectory+type+".sqlite",
                "",
                "",
                type,
                "org.sqlite.JDBC");


        DruidDataSource druidDataSource = dataSourceUtils.findDataSource(dataSourceInfo.getDatasourceKey());

        if(druidDataSource == null){
            //测试数据源连接
            log.debug("this datasource is null");
            druidDataSource = dataSourceUtils.createDataSourceConnection(dataSourceInfo);
        }
        log.debug("this datasource is not null");
        if (Objects.nonNull(druidDataSource)){
            //将新的数据源连接添加到目标数据源map中
            dataSourceUtils.addDefineDynamicDataSource(druidDataSource,dataSourceInfo.getDatasourceKey());
            //设置当前线程数据源名称-----代码形式
            DynamicDataSourceHolder.setDynamicDataSourceKey(dataSourceInfo.getDatasourceKey());
            //在新的数据源中查询用户信息

            try{
                log.debug("query start:"+System.currentTimeMillis());
                res = commonMapper.querySql(sql);


            }catch(Exception e){
                e.printStackTrace();
            }
            log.debug("query end:"+System.currentTimeMillis());

            //关闭数据源连接
            //druidDataSource.close();
        }
        return res;
    }
    /*@ApiOperation(value="方法二：使用注解方式动态切换多数据源", notes="test02")
    @GetMapping("/test02")
    public Student test02(String id){
//        Student student=studentMapper.findStudentById(id);
        return studentService.findStudentById(id);
    }*/
}
