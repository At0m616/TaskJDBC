package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/test_db?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "R00t_P@$$";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();

            properties.put(Environment.URL, URL);
            properties.put(Environment.DRIVER, DRIVER);
            properties.put(Environment.USER, USERNAME);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.DEFAULT_SCHEMA, "test_db");
            properties.put(Environment.JDBC_TIME_ZONE,"UTC");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Class.forName(DRIVER);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
    }

    public static void closeConnection(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {

            }
        }
    }

    public static void rollBack(Connection conn){
        if(conn != null){
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
