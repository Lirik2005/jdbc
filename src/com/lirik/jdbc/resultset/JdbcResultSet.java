package com.lirik.jdbc.resultset;

import com.lirik.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcResultSet {

    public static void main(String[] args) throws SQLException {

        String sql = """
                SELECT *
                FROM ticket
                 """;

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
          while (resultSet.next()) {
              System.out.println(resultSet.getLong("id"));
              System.out.println(resultSet.getString("passenger_no"));
              System.out.println(resultSet.getBigDecimal("cost"));
              System.out.println("-----------------");
          }
        }
    }

}
