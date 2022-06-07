package com.lirik.jdbc;

import com.lirik.jdbc.util.ConnectionManager;

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
         * Для удобства создания подключения создаем утилитный класс ConnectionManager куда выносим наше подключение, обрабатываем
         * исключение и в дальнейшем только вызываем его в блоке try-with-resources
         */

        try (Connection openConnection = ConnectionManager.open()) {
            System.out.println(openConnection.getTransactionIsolation());   // Выводим уровень изолированности транзакции (см. курс по SQL)

        }


    }

}
