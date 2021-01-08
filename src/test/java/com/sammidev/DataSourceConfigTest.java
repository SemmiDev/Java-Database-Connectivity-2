package com.sammidev;

import com.sammidev.config.DataSourceConfig;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.SQLException;

@Slf4j
public class DataSourceConfigTest extends TestCase {

    private DataSourceConfig config;

/*    @Override
    public void setUp() throws Exception {
        this.config = new DataSourceConfig(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "sammidev");
    }

    @Test
    public void testConnectionToDB1() throws SQLException {
        var dataSource = this.config.getDataSource();
        var connection = dataSource.getConnection();
        log.info("CONNECTED");
    }
*/

    @Override
    protected void setUp() throws Exception {
        this.config = new DataSourceConfig();
    }

    @Test
    public void testConnectionToDB2() throws SQLException {
        var dataSource = this.config.getDataSource();
        var connection = dataSource.getConnection();
        log.info("CONNECTED");
    }
}