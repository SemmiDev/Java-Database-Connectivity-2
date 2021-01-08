package com.sammidev.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class DataSourceConfig {

    private final HikariConfig config;

    public DataSourceConfig() {
        var properties = new Properties();
        var input = DataSourceConfig.class
                .getResourceAsStream("/application.properties");
        try {
            properties.load(input);
            input.close();
        } catch (IOException e) {
            log.error("can't load file property");
        }

        this.config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("jdbc.url"));
        config.setUsername(properties.getProperty("jdbc.username"));
        config.setPassword(properties.getProperty("jdbc.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    /*public DataSourceConfig(String url, String username, String password) {
        this.config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }
   */

    public DataSource getDataSource() {
        return new HikariDataSource(config);
    }
}