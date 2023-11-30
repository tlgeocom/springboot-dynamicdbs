package com.wonders.controller;

import com.alibaba.druid.pool.DruidDataSource;
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
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentServiceImpl studentService;  //一定要注册实现层才可以
    @ApiOperation(value="方法一：声明方式动态切换多数据源测试", notes="test")
    @GetMapping("/test")
    public Map<String, Object> dynamicDataSourceTest(String id){
        Map<String, Object> map = new HashMap<>();
        //1、默认库中查询数据
        Student student=studentMapper.selectById(id);
        map.put("1、默认库中查询到的数据",student);
        //2、指定库中查询的数据
        DynamicDataSourceHolder.setDynamicDataSourceKey(DbsConstant.mysql_db_02);
        Student student02=studentMapper.selectById(id);
        map.put("2、指定库中查询的数据",student02);
        //3、从数据库获取连接信息，然后获取数据
        //模拟从数据库中获取的连接
        DataSourceInfo dataSourceInfo = new DataSourceInfo(
                "jdbc:mysql://127.0.0.1:3308/test02?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                 "root",
                "root",
                "mysqldb03",
                "com.mysql.cj.jdbc.Driver");
        map.put("dataSource",dataSourceInfo);
        log.info("数据源信息：{}",dataSourceInfo);
        //测试数据源连接
        DruidDataSource druidDataSource = dataSourceUtils.createDataSourceConnection(dataSourceInfo);
        if (Objects.nonNull(druidDataSource)){
            //将新的数据源连接添加到目标数据源map中
            dataSourceUtils.addDefineDynamicDataSource(druidDataSource,dataSourceInfo.getDatasourceKey());
            //设置当前线程数据源名称-----代码形式
            DynamicDataSourceHolder.setDynamicDataSourceKey(dataSourceInfo.getDatasourceKey());
            //在新的数据源中查询用户信息
            Student student03=studentMapper.selectById(id);
            map.put("3、动态数据源查询的数据",student03);
            //关闭数据源连接
            druidDataSource.close();
        }
        //4、指定oracle库中查询的数据
        DynamicDataSourceHolder.setDynamicDataSourceKey(DbsConstant.oracle_db_01);
        Student student04=studentMapper.selectById(id);
        map.put("4、指定oracle库中查询的数据",student04);
        return map;
    }
    @ApiOperation(value="方法二：使用注解方式动态切换多数据源", notes="test02")
    @GetMapping("/test02")
    public Student test02(String id){
//        Student student=studentMapper.findStudentById(id);
        return studentService.findStudentById(id);
    }
}
