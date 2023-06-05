package com.miit.sep22.java.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

import javax.management.*;
import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;


public class HikariCPDataSourceFactory implements DataSourceFactory {
    private HikariDataSource dataSource;

    @Override
    public void setProperties(Properties props) {
        HikariConfig config = new HikariConfig(props);
        this.dataSource = new HikariDataSource(config);
        getPoolInfo();
    }

    public static void getPoolInfo() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName poolName = null;
        HashMap<String, Integer> idleConnections = new HashMap<>();
        try {
            poolName = new ObjectName("com.zaxxer.hikari:type=Pool (test)");
            idleConnections.put("IdleConnections", (Integer) mBeanServer.getAttribute(poolName, "IdleConnections"));
            idleConnections.put("ActiveConnections", (Integer) mBeanServer.getAttribute(poolName, "ActiveConnections"));
            idleConnections.put("TotalConnections", (Integer) mBeanServer.getAttribute(poolName, "TotalConnections"));
            idleConnections.put("ThreadsAwaitingConnection", (Integer) mBeanServer.getAttribute(poolName, "ThreadsAwaitingConnection"));

         /*   MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(poolName);
            Arrays.stream(mBeanInfo.getAttributes())
                    .forEach(mBeanAttributeInfo -> System.out.println(mBeanAttributeInfo));*/
        } catch (Exception e) {
             e.printStackTrace();
        }


        System.out.println("Number of Idle Connections : " + idleConnections);
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
}
/*public class HikariCPDataSourceFactory extends PooledDataSourceFactory {

    public HikariCPDataSourceFactory() {
        // HikariConfig hikariConfig = new HikariConfig();
        this.dataSource = new HikariDataSource();
    }
}*/
