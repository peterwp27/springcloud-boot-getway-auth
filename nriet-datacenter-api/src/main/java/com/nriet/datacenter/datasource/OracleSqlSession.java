//package com.nriet.datacenter.datasource;
////package com.nriet.datasource;
////
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@MapperScan(basePackages = "com.nriet.datacenter.mapper", sqlSessionTemplateRef = "oracleSqlSessionTemplate")
//public class OracleSqlSession {
//
//    @Bean(name = "oracleSqlSessionFactory")
//    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("oracleDataSource") DataSource dataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:com/nriet/datacenter/mapper/mhzl/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "oracleTransactionManager")
//    public PlatformTransactionManager secondaryTransactionManager(
//            @Qualifier("oracleDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "oracleSqlSessionTemplate")
//    public SqlSessionTemplate secondarySqlSessionTemplate(
//            @Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}