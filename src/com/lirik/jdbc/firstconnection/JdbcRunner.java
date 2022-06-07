package com.lirik.jdbc.firstconnection;

import com.lirik.jdbc.util.ConnectionManager;
import com.lirik.jdbc.util.ConnectionManagerDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

        String username = "postgres";
        String password = "postgres";
        String url = "jdbc:postgresql://localhost:5433/flight_repository";

        /**
         * Таким образом устанавливается соединение с базой данных!!!
         * Также необходимо использовать try-with-resources, чтобы закрыть подключение!!!
         */

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println(connection.getTransactionIsolation());   // Выводим уровень изолированности транзакции (см. курс по SQL)
        }

        /**
         * Для удобства создания подключения создаем утилитный класс ConnectionManagerDemo куда выносим наше подключение, обрабатываем
         * исключение и в дальнейшем только вызываем его в блоке try-with-resources
         */

        try (Connection openDemoConnection = ConnectionManagerDemo.open()) {
            System.out.println(
                    openDemoConnection.getTransactionIsolation());   // Выводим уровень изолированности транзакции (см. курс по SQL)

        }

        /**
         * Для ПРАВИЛЬНОГО подключения к базе данных создаем файл application.properties, где указываем креды для подключения; утилитный
         * класс PropertiesUtil ОДИН РАЗ загружает этот файл в статическом блоке инициализации и позволяет нам в классе ConnectionManager
         * получить доступ к данным для подключения. После чего мы создаем подключение, обрабатываем исключение и в дальнейшем вызываем
         * его в блоке try-with-resources
         */

        try (Connection openUtilConnection = ConnectionManager.open()) {
            System.out.println(
                    openUtilConnection.getTransactionIsolation());   // Выводим уровень изолированности транзакции (см. курс по SQL)
        }
    }
}
