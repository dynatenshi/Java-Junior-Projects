package TicketOrderSystem.classes;

import TicketOrderSystem.interfaces.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Ticket> tickets = List.of(
                new Ticket(1, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 1500.0),
                new Ticket(2, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 1500.0),
                new Ticket(3, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2000.0),
                new Ticket(4, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2000.0),
                new Ticket(5, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2500.0),
                new Ticket(6, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2500.0)
        );

        TicketService service = new ConcurrentTicketService(tickets);

        for (Ticket t : service.getAllTickets()) {
            System.out.println(t);
        };

        System.out.println("Available count:"+service.getAvailableTicketCount());

        for (Map.Entry<String, Integer> entry : service.getEventStatistics().entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }

        service.purchaseTicket(1, "amogus@gmail.com");
        service.reserveTicket(2, "godofwar@mail.ru");

        System.out.println("Available count:"+service.getAvailableTicketCount());
        for (Map.Entry<String, Integer> entry : service.getEventStatistics().entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }

        for (Ticket t : service.getAllTickets()) {
            System.out.println(t);
        };

        System.out.println("Reservations of godofwar@mail.ru: ");
        for (Ticket t: service.getUserReservations("godofwar@mail.ru")) {
            System.out.println(t);
        };

        System.out.println("Canceling reservations of ticked No 2");
        service.cancelReservation(2);

        System.out.println("Reservations of godofwar@mail.ru: ");
        for (Ticket t: service.getUserReservations("godofwar@mail.ru")) {
            System.out.println(t);
        };

        System.out.println("Available count:"+service.getAvailableTicketCount());
        for (Map.Entry<String, Integer> entry : service.getEventStatistics().entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }

        for (Ticket t : service.getAllTickets()) {
            System.out.println(t);
        };

        System.out.println("Revenue:"+service.getTotalRevenue());

        service.reserveTicket(1, "godofwar@mail.ru");
        service.cancelReservation(1);

        for (Ticket t : service.getAllTickets()) {
            System.out.println(t);
        };
    }
}
