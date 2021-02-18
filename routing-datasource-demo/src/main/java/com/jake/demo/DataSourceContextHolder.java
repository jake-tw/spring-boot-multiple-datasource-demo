package com.jake.demo;

import org.springframework.util.Assert;

import com.jake.demo.model.DataSourceType;

public class DataSourceContextHolder {

    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();

    public static void setType(DataSourceType type) {
        Assert.notNull(type, "data source type cannot be null");
        contextHolder.set(type);
    }

    public static DataSourceType getType() {
        return contextHolder.get();
    }

    public static void clearType() {
        contextHolder.remove();
    }
}
