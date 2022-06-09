package com.lirik.jdbc.sqlinjection;

import com.lirik.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqlInjection {

    public static void main(String[] args) throws SQLException {

        /**
         * Код, который реализован в этом классе уязвим из-за SQL-injection. В переменной flightId мы можем кроме id'шника через ";" мы
         * можем передать любую команду, которая хоть и вызовет exception, но все равно выполнится.
         * Например, если мы сделаем String flightId = "2 OR 1=1", то мы получим список всех id, а не только тех у кого flightId = 2.
         * Если будет String flightId = "2; DROP TABLE ticket", то в консоль упадет ошибка, но таблица будет удалена все равно!!!
         * Вывод: SQL-injection ОЧЕНЬ ПЛОХО!!!
         */

        String flightId = "2";
        List<Long> result = getTicketsByFlightId(flightId);
        System.out.println(result);

    }

    private static List<Long> getTicketsByFlightId(String flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE  flight_id =%s
                """.formatted(flightId);
        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
//                result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // так будет немного правильнее, чкм строкой выше NULL safe
            }
        }
        return result;
    }
}
