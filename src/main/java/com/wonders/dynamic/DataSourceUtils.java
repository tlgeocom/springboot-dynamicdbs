package com.wonders.dynamic;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Description: TODO：数据源工具类
 * @Author: yyalin
 * @CreateDate: 2023/7/16 15:00
 * @Version: V1.0
 */
@Slf4j
@Component
public class DataSourceUtils {
    @Resource
    DynamicDataSource dynamicDataSource;

    /**
     * @Description: 根据传递的数据源信息测试数据库连接
     * @Author zhangyu
     */
    public DruidDataSource createDataSourceConnection(DataSourceInfo dataSourceInfo) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceInfo.getUrl());
        druidDataSource.setUsername(dataSourceInfo.getUserName());
        druidDataSource.setPassword(dataSourceInfo.getPassword());
        druidDataSource.setDriverClassName(dataSourceInfo.getDriverClassName());
        druidDataSource.setBreakAfterAcquireFailure(true);
        druidDataSource.setConnectionErrorRetryAttempts(0);
        try {
            druidDataSource.getConnection(2000);
            log.info("数据源连接成功");
            return druidDataSource;
        } catch (SQLException throwables) {
            log.error("数据源 {} 连接失败,用户名：{}，密码 {}",dataSourceInfo.getUrl(),dataSourceInfo.getUserName(),dataSourceInfo.getPassword());
            return null;
        }
    }

    /**
     * @Description: 将新增的数据源加入到备份数据源map中
     * @Author zhangyu
     */
    public void addDefineDynamicDataSource(DruidDataSource druidDataSource, String dataSourceName){
        Map<Object, Object> defineTargetDataSources = dynamicDataSource.getDefineTargetDataSources();
        defineTargetDataSources.put(dataSourceName, druidDataSource);
        dynamicDataSource.setTargetDataSources(defineTargetDataSources);
        dynamicDataSource.afterPropertiesSet();
    }
}
