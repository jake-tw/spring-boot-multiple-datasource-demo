package com.jake.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.jake.demo.DataSourceContextHolder;
import com.jake.demo.model.DataSourceType;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getType();
    }

    public RoutingDataSource(Map<DataSourceType, DataSource> dataSource) {
        Map<Object, Object> target = new HashMap<Object, Object>();

        dataSource.forEach((key, ds) -> {
            target.put(key, ds);
        });

        setTargetDataSources(target);
        setDefaultTargetDataSource(target.get(DataSourceType.MAIN));
    }
}
