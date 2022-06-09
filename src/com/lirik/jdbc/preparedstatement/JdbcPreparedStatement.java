package com.lirik.jdbc.preparedstatement;

import com.lirik.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcPreparedStatement {

    public static void main(String[] args) throws SQLException {

        Long flightId = 2L;
        List<Long> result = getTicketsByFlightId(flightId);
        System.out.println(result);

        List<Long> flightsBetween = getFlightsBetween(LocalDate.of(2020, 10, 1).atStartOfDay(), LocalDateTime.now());
        System.out.println(flightsBetween);
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {

        List<Long> result = new ArrayList<>();

        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));   // так тоже можно, но это не NULL safe (НО НЕ СТРАШНО)
            }
        }
        return result;
    }

    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {

        /**
         * Суть PreparedStatement в том, что параметры запроса не надо форматировать как в примере с SQL-injection. Все неизвестные
         * параметры перечисляются через знак "?" и соответственно вызов метода formatted() уже не требуется!!!
         */

        String sql = """
                SELECT id
                FROM ticket
                WHERE  flight_id =?
                """;
        List<Long> result = new ArrayList<>();

        /**
         * Чтобы избежать SQL-injection используем у нашего connection метод prepareStatement(), в который и передаем наш sql-запрос.
         * Больше нигде мы этот запрос не отдаем и методы execute(), executeQuery() и т.д. уже ничего принимать не должны!!!
         */

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            /**
             * В методе setLong() первым параметром приходит порядковый номер нашего "?" в SQL-запроса (отсчет начинается с единицы) и
             * вторым параметром собственно значение, на которое меняется наш "?"!!!
             */

            preparedStatement.setLong(1, flightId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class)); // так будет немного правильнее, чкм строкой выше NULL safe
            }
        }
        return result;

    }

}
