package com.lirik.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManagerDemo {

    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5433/flight_repository";

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

    private ConnectionManagerDemo() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
