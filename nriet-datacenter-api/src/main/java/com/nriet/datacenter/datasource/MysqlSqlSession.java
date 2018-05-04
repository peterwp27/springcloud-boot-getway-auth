package com.nriet.datacenter.datasource;
//package com.nriet.datasource;
//
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
//
///**
// * spring 整合 mybatis 配置SqlSessionTemplate
// * 
// *
// */
@Configuration
@MapperScan(basePackages = "com.nriet.datacenter.mapper.cpzz", sqlSessionTemplateRef  = "mysqlSqlSessionTemplate")
public class MysqlSqlSession {
    
    @Bean(name = "mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/nriet/datacenter/mapper/cpzz/*.xml"));
        return bean.getObject();
    }

    //配置声明式事务管理器
    @Bean(name = "mysqlTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
}
