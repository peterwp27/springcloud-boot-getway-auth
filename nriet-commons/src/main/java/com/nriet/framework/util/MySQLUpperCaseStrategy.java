package com.nriet.framework.util;

import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.stereotype.Component;
/**
 * mysql忽略数据库表名大小写的转化
 * @author b_wangpei
 *
 */
@Component
public class MySQLUpperCaseStrategy extends SpringPhysicalNamingStrategy {
    @Override
    protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
        return false;
    }
}
