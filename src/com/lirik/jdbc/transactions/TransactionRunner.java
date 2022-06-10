package com.lirik.jdbc.transactions;

import com.lirik.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {

    public static void main(String[] args) throws SQLException {

        long flightId = 9;

        /**
         * Если запроса на удаление ticket не будет, то получим ошибку, так как таблицы связаны между собой!!!
         * Если не удалить билеты, то рейсы мы не сможем убрать из таблицы!!!
         */

        String deleteFlightSql = "DELETE FROM flight WHERE id = ?";
        String deleteTicketSql = "DELETE FROM ticket WHERE flight_id = ?";

        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketStatement = null;
        try {
            connection = ConnectionManager.open();
            deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
            deleteTicketStatement = connection.prepareStatement(deleteTicketSql);
            connection.setAutoCommit(false);   // забираем управление транзакциями на себя!!! Делаем это до выполнения запросов

            deleteFlightStatement.setLong(1, flightId);
            deleteTicketStatement.setLong(1, flightId);

            deleteTicketStatement.executeUpdate();    // выполняем запрос
            deleteFlightStatement.executeUpdate();    // выполняем запрос

            connection.commit(); // таким образом фиксируем транзакцию и удалятся все записи
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteFlightStatement != null) {
                deleteFlightStatement.close();
            }
            if (deleteTicketStatement != null) {
                deleteTicketStatement.close();
            }
        }
    }
}
