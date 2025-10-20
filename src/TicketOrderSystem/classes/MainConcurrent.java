package TicketOrderSystem.classes;

import TicketOrderSystem.interfaces.TicketService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainConcurrent {
    public static void main(String[] args) throws InterruptedException {
        List<Ticket> tickets = List.of(
                new Ticket(1, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 1500.0),
                new Ticket(2, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 1500.0),
                new Ticket(3, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2000.0),
                new Ticket(4, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2000.0),
                new Ticket(5, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2500.0),
                new Ticket(6, "Rock Concert", LocalDateTime.of(2024, 6, 15, 20, 0), 2500.0)
        );

        TicketService service = new ConcurrentTicketService(tickets);

        ExecutorService executor = Executors.newFixedThreadPool(50);
        List<Future<Boolean>> results = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            final int ticketId = (i % 6) + 1;
            final String email = "user"+i+"@mail.ru";
            results.add(executor.submit(() -> service.reserveTicket(ticketId, email)));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        long successfulReservations = results.stream().filter(f -> {
            try { return f.get(); } catch (Exception e) { return false; }
        }).count();

        System.out.println((6 == successfulReservations));
    }
}
