package NTNU.IDATT1002.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * TODO:
 *      java.sql.SQLException: Access denied for user 'eirsteir_root'@'dhcp-10-22-12-209.wlan.ntnu.no' (using password: YES)
 */
public class Database {

    private static final Logger logger = Logger.getLogger(Database.class.getName());
    private static HikariConfig config;
    private static HikariDataSource dataSource;

    /**
     * Load configuration and setup the data source
     */
    static{
        config = new HikariConfig("datasource.properties" );;
        dataSource = new HikariDataSource(config);
    }

    /**
     * Gets a connection from the HikariCP connection pool
     * @return Connection to the database
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}