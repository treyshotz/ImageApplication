package NTNU.IDATT1002.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for connecting to the database. This will load configurations and create a connection pool.
*/
public class DBConnection {

    private static HikariDataSource dataSource;

    /**
     * Load configuration and setup the data source
     */
    static{
        HikariConfig config = new HikariConfig("datasource.properties" );
        dataSource = new HikariDataSource(config);
    }

    /**
     * Establish a connection pool to the database
     *
     * @return Connection to the database
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}