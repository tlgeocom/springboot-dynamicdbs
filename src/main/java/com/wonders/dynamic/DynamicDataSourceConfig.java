package com.wonders.dynamic;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO： 数据源信息配置类，读取数据源配置信息并注册成bean。
 * @Author: yyalin
 * @CreateDate: 2023/7/16 14:54
 * @Version: V1.0
 */
@Configuration
@MapperScan("com.wonders.dao")
@Slf4j
public class DynamicDataSourceConfig {
    @Bean(name = DbsConstant.mysql_db_01)
    @ConfigurationProperties("spring.datasource.mysqldb01")
    public DataSource masterDataSource() {
        log.info("数据源切换为：{}",DbsConstant.mysql_db_01);
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = DbsConstant.mysql_db_02)
    @ConfigurationProperties("spring.datasource.mysqldb02")
    public DataSource slaveDataSource() {
        log.info("数据源切换为：{}",DbsConstant.mysql_db_02);
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }


    @Bean(name = DbsConstant.oracle_db_01)
    @ConfigurationProperties("spring.datasource.oracledb01")
    public DataSource oracleDataSource() {
        log.info("数据源切换为oracle：{}",DbsConstant.oracle_db_01);
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }
    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(){
        Map<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(DbsConstant.mysql_db_01,masterDataSource());
        dataSourceMap.put(DbsConstant.mysql_db_02,slaveDataSource());
        dataSourceMap.put(DbsConstant.oracle_db_01,oracleDataSource());
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        //将数据源信息备份在defineTargetDataSources中
        dynamicDataSource.setDefineTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

}
