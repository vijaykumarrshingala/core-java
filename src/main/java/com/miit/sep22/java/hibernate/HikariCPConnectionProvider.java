package com.miit.sep22.java.hibernate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPConnectionProvider implements ConnectionProvider {


    private static final long DEFAULT_MAX_LIFE_TIME_MILLIS = 1200000;
    private static final long DEFAULT_IDLE_TIMEOUT_MILLIS = 600000;
    private static final long DEFAULT_CONN_TIMEOUT_MILLIS = 30000;
    private static final int DEFAULT_MIN_IDLE_CONNECTION = 2;
    private static final int DEFAULT_MAX_CONNECTION = 10;
    private HikariDataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void close() throws HibernateException {

    }

    @Override
    public void configure(Properties properties) {
        HikariConfig config = new HikariConfig();

        // Set HikariCP configuration properties
        config.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
        config.setUsername(properties.getProperty("hibernate.connection.username"));
        config.setPassword(properties.getProperty("hibernate.connection.password"));

        String maximumPoolSize = properties.getProperty("hibernate.hikari.maximumPoolSize");
        String minimumIdle = properties.getProperty("hibernate.hikari.minimumIdle");
        String connectionTimeout = properties.getProperty("hibernate.hikari.connectionTimeout");
        String idleTimeout = properties.getProperty("hibernate.hikari.idleTimeout");
        String maxLifetime = properties.getProperty("hibernate.hikari.maxLifetime");

        config.setMaximumPoolSize( maximumPoolSize == null ? DEFAULT_MAX_CONNECTION : Integer.parseInt(maximumPoolSize));
        config.setMinimumIdle(minimumIdle == null ? DEFAULT_MIN_IDLE_CONNECTION : Integer.parseInt(minimumIdle));
        config.setConnectionTimeout( connectionTimeout == null ? DEFAULT_CONN_TIMEOUT_MILLIS : Long.parseLong(connectionTimeout));
        config.setIdleTimeout( idleTimeout == null ? DEFAULT_IDLE_TIMEOUT_MILLIS : Long.parseLong(idleTimeout));
        config.setMaxLifetime( maxLifetime == null ? DEFAULT_MAX_LIFE_TIME_MILLIS : Long.parseLong(maxLifetime));

        // Performance tuning
        /*config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");*/

        // Create the HikariCP data source
        dataSource = new HikariDataSource(config);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }
}

