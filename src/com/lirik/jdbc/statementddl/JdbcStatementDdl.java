package com.lirik.jdbc.statementddl;

import com.lirik.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcStatementDdl {

    public static void main(String[] args) throws SQLException {

        /**
         * Такое использование кавычек называется текст-блок и пришел из JAVA 15. В нем мы пишем SQL-запрос.
         * Чтобы IntelliJ IDEA стала подсказывать код надо нажать: на МAC -> option+Enter; на Windows -> alt+Enter. Далее выбрать поле
         * Inject language or reference и там выбрать PostgreSQL.
         */


        String createDatabase = """
                CREATE DATABASE game;
                 """;

        /**
         * Соединение у statement открываем в блоке try-with-resources после создания нашего подключения к базе данных!!!
         */

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {

            /**
             * Метод execute() может выполнить любую строку (т.е. SQL-запрос).
             * Операция ниже будет возвращать false, так как запрос у нас ничего не возвращает как например запрос select, но базу данных
             * создаст!!!
             * Через метод execute() можно выполнять любые sql-операции, НО в основном этим методом выполняют ТОЛЬКО DDL-операции!!!
             */

            boolean execute = statement.execute(createDatabase);
            System.out.println(execute);}

    }

}
