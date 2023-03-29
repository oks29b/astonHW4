package org.example.listener;


import org.example.config.db.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class AppListener implements ServletContextListener {
    public static final String SQL_FILE_NAME = "src/main/resources/schema.sql";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initDb();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.closePool();
    }

    private void initDb(){
        try(
            InputStream inputStream = AppListener.class.getClassLoader().getResourceAsStream(SQL_FILE_NAME);
            Connection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
        ){
            if(inputStream == null) return;
            String sql = new String(inputStream.readAllBytes());
            statement.execute(sql);
        }catch (IOException | SQLException exception){
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }
}
