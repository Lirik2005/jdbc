package com.lirik.jdbc.statementdml;

import com.lirik.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcStatementDml {

    public static void main(String[] args) throws SQLException {

        /**
         * В текстовом блоке можно писать сразу несколько запросов
         */

        String sql = """
                CREATE TABLE IF NOT EXISTS info (
                id SERIAL PRIMARY KEY ,
                data TEXT NOT NULL)
                 """;

        String insert = """
                 INSERT INTO info (data)
                 VALUES 
                ('TEST1'),
                ('TEST2'),
                ('TEST3'),
                ('TEST4')
                 """;

        /**
         * Для операций update, insert и  delete рекомендуется использовать метод executeUpdate()
         */

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            boolean execute = statement.execute(sql);
            System.out.println(execute);
            int i = statement.executeUpdate(insert);
            System.out.println(i);
        }

    }

}
