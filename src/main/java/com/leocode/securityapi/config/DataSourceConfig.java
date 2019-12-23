package com.leocode.securityapi.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    /*@Bean
    public DataSource getDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/hr?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("leo123456");
        return dataSourceBuilder.build();
    }*/
}
