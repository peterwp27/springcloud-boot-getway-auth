package com.nriet.datacenter.datasource;
//package com.nriet.datasource;
//
import javax.sql.DataSource;

//
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
//
///**
// * 配置多数据源
// * 
// * 
// *
// */
@Configuration
public class MultiDataSourceConfig {

    @Bean(name = "mysqlDataSource")
    @Qualifier("mysqlDataSource")
    @Primary // 定义主数据源
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
    	return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }

//    @Bean(name = "oracleDataSource")
//    @Qualifier("oracleDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.oracle")
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
}