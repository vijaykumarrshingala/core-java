package com.miit.sep22.java.mybatis;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

public class DbcpDataSourceFactory extends UnpooledDataSourceFactory {

    public DbcpDataSourceFactory() {
        this.dataSource = new BasicDataSource();
    }

}
