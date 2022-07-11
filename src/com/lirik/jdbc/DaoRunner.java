package com.lirik.jdbc;

import com.lirik.jdbc.dao.TicketDao;
import com.lirik.jdbc.dto.TicketFilter;
import com.lirik.jdbc.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {

        TicketDao ticketDao = TicketDao.getInstance();

        TicketFilter ticketFilter = new TicketFilter(3, 0, "Иван Иванов", "A1");
        List<Ticket> all = ticketDao.findAll(ticketFilter);
        System.out.println(all);

//        saveTest();

//        boolean delete = ticketDao.delete(58L);
//        System.out.println(delete);

//        updateTest(ticketDao);

//        List<Ticket> tickets = ticketDao.findAll();
//        for (Ticket ticket : tickets) {
//            System.out.println(ticket);
//        }

    }

    private static void updateTest(TicketDao ticketDao) {
        Optional<Ticket> ticket = ticketDao.findById(55L);
        System.out.println(ticket);

        ticket.ifPresent(ticket1 -> {
            ticket1.setCost(BigDecimal.valueOf(1000));
            ticketDao.update(ticket1);
        });
    }

    private static void saveTest(TicketDao ticketDao) {
        Ticket ticket = new Ticket();
        ticket.setPassengerNo("1234567");
        ticket.setPassengerName("Test");
        ticket.setFlightId(3L);
        ticket.setSeatNo("B3");
        ticket.setCost(BigDecimal.TEN);

        Ticket savedTicket = ticketDao.save(ticket);
        System.out.println(savedTicket);
    }
}
