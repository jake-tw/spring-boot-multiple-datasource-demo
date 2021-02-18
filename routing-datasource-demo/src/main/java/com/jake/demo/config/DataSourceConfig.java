package com.jake.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.jake.demo.model.DataSourceType;

@Configuration
public class DataSourceConfig {
    
    @Bean("main")
    @ConfigurationProperties("spring.datasource.main")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("sub")
    @ConfigurationProperties("spring.datasource.sub")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @Primary
    public DataSource routingDataSource(@Qualifier("main") DataSource main, 
                                        @Qualifier("sub") DataSource sub) {
        Map<DataSourceType, DataSource> dataSources = new HashMap<DataSourceType, DataSource>();
        dataSources.put(DataSourceType.MAIN, main);
        dataSources.put(DataSourceType.SUB, sub);
        return new RoutingDataSource(dataSources);
    }
}
