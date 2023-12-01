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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: TODO
 * @Author: yyalin
 * @CreateDate: 2023/7/16 15:02
 * @Version: V1.0
 */
@Slf4j
@Api(tags="动态切换多数据源测试")
@RestController
public class TestController {
    @Resource
    DataSourceUtils dataSourceUtils;
    //@Autowired
    //private StudentMapper studentMapper;
    @Autowired
    private CommonMapper commonMapper;
    //@Autowired
    //private StudentServiceImpl studentService;  //一定要注册实现层才可以
    @ApiOperation(value="方法一：声明方式动态切换多数据源测试", notes="test")
    @GetMapping("/terrain/{z}/{x}/{y}.terrain")
    public Map<String, Object> dynamicDataSourceTest(@PathVariable Integer z, @PathVariable Integer x, @PathVariable Integer y){
        Map<String, Object> map = new HashMap<>();

        String tableName = "";

        if(z < 10){
            tableName = "blocks";
        }else{
            tableName = "blocks_"+String.valueOf(z)+"_"+String.valueOf(x/512)+"_"+String.valueOf(y/512);
        }
        String sql = "select z||x||y as res from "+tableName+" where z="+z+" and x="+x+" and y="+y+" limit 1";
        log.debug("sql:"+sql);
        //1、默认库中查询数据
        String res1 = "";
        try{
            log.info("query start:"+System.currentTimeMillis());
            res1 = commonMapper.querySql(sql);

        }catch(Exception e){

        }
        log.info("query end:"+System.currentTimeMillis());
        map.put("res1",res1);
        //3、从数据库获取连接信息，然后获取数据
        //模拟从数据库中获取的连接
        DataSourceInfo dataSourceInfo = new DataSourceInfo(
                "jdbc:sqlite:D:/test_2.pak",
                 "",
                "",
                "sqlite02123",
                "org.sqlite.JDBC");

        log.info("数据源信息：{}",dataSourceInfo);
        DruidDataSource druidDataSource = dataSourceUtils.findDataSource(dataSourceInfo.getDatasourceKey());

        if(druidDataSource == null){
            //测试数据源连接
            log.info("this datasource is null");
            druidDataSource = dataSourceUtils.createDataSourceConnection(dataSourceInfo);
        }
        log.info("this datasource is not null");
        if (Objects.nonNull(druidDataSource)){
            //将新的数据源连接添加到目标数据源map中
            dataSourceUtils.addDefineDynamicDataSource(druidDataSource,dataSourceInfo.getDatasourceKey());
            //设置当前线程数据源名称-----代码形式
            DynamicDataSourceHolder.setDynamicDataSourceKey(dataSourceInfo.getDatasourceKey());
            //在新的数据源中查询用户信息
            String res2 = "";
            try{
                log.info("query start:"+System.currentTimeMillis());
                res2 = commonMapper.querySql(sql);

            }catch(Exception e){

            }
            log.info("query end:"+System.currentTimeMillis());
            map.put("res2",res2);
            //关闭数据源连接
            //druidDataSource.close();
        }

        return map;
    }
    /*@ApiOperation(value="方法二：使用注解方式动态切换多数据源", notes="test02")
    @GetMapping("/test02")
    public Student test02(String id){
//        Student student=studentMapper.findStudentById(id);
        return studentService.findStudentById(id);
    }*/
}
