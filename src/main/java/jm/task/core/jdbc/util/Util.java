package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String USER = "root";
    private static final String PASSWORD = "test";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(DRIVER, DRIVER);
            settings.put(AvailableSettings.USER, USER);
            settings.put(AvailableSettings.PASS, PASSWORD);
            settings.put(AvailableSettings.URL, URL);
            settings.put(DIALECT, DIALECT);
            settings.put(AvailableSettings.SHOW_SQL, "true");
            settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(AvailableSettings.HBM2DDL_AUTO, "create-drop");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        SessionFactory sessionFactory = getSessionFactory();
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
