package com.lirik.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String USERNAME_KEY = "database.username";
    private static final String PASSWORD_KEY = "database.password";
    private static final String URL_KEY = "database.url";

    /**
     * В JAVA до 1.8 могут быть проблемы с драйверами подключаемыми через дополнительные библиотеки. Для этого создаем этот статический
     * блок инициализации и через метод Class.forName() загружаем драйвер.
     * После JAVA 1.8 подобные манипуляции НЕ ТРЕБУЮТСЯ!!!
     */

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
