package org.example.config.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    public static final String FILE_PATH = "src/main/resources/db.properties";
    public static final String DB_USERNAME_KEY = "db.user";
    public static final String DB_PASSWORD_KEY = "db.password";
    public static final String DB_URL_KEY = "db.url";
    public static final String MAX_CONNECTION_POOL_SIZE_KEY = "db.connection.poolsize.max";

    private static ConnectionPool connectionPool = new ConnectionPool();
    private Properties properties;
    private static ComboPooledDataSource pooledDataSource;
    private ConnectionPool(){
        properties = new Properties();
        try(InputStream inputStream = new FileInputStream(FILE_PATH)){
            properties.load(inputStream);
            pooledDataSource = new ComboPooledDataSource();
            pooledDataSource.setJdbcUrl(properties.getProperty(DB_URL_KEY));
            pooledDataSource.setUser(properties.getProperty(DB_USERNAME_KEY));
            pooledDataSource.setPassword(properties.getProperty(DB_PASSWORD_KEY));
            pooledDataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty(MAX_CONNECTION_POOL_SIZE_KEY)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance(){
        return connectionPool;
    }

    public Connection getConnection(){
        try{
            return pooledDataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void closePool(){
        pooledDataSource.close();
    }
}
