package TicketOrderSystem.classes;

import TicketOrderSystem.interfaces.TicketService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ConcurrentTicketService implements TicketService {
    private final ConcurrentMap<Integer, Ticket> tickets;

    public ConcurrentTicketService(List<Ticket> tickets) {
        this.tickets = new ConcurrentHashMap<>(tickets.stream()
                .collect(Collectors
                        .toMap(Ticket::getId,
                                t -> t,
                                (oldVal, newVal) -> oldVal
                        )));
    }
    public ConcurrentTicketService(Map<Integer, Ticket> tickets) {
        this.tickets = new ConcurrentHashMap<>(tickets);
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<Ticket>(tickets.values());
    }

    public List<Ticket> getAvailableTickets() {
        return tickets.values().stream()
                .filter(t -> t.getStatus().equals(Ticket.TicketStatus.AVAILABLE))
                .collect(Collectors.toList());
    }

    public int getAvailableTicketCount() {
        return (int) tickets.values().stream()
                .filter(t -> t.getStatus().equals(Ticket.TicketStatus.AVAILABLE))
                .count();
    }

    public boolean reserveTicket(int ticketId, String userEmail) {
        Ticket t = tickets.get(ticketId);
        if (t == null) throw new IllegalArgumentException("No ticket with that id");

        synchronized (t) {
            if (t.getStatus() != Ticket.TicketStatus.AVAILABLE) {
                System.out.println("Ticket No "+ticketId+" is already reserved");
                return false;
            }

            t.setStatus(Ticket.TicketStatus.RESERVED);
            t.setOwnerEmail(userEmail);
            System.out.println("Ticket No "+ticketId+" successfully reserved");
            return true;
        }
    }

    public boolean purchaseTicket(int ticketId, String userEmail) {
        Ticket t = tickets.get(ticketId);
        if (t == null) throw new IllegalArgumentException("No ticket with that id");

        synchronized (t) {
            if (t.getStatus() == Ticket.TicketStatus.SOLD) {
                System.out.println("Ticket No "+ticketId+" is already sold");
                return false;
            }
            if (t.getStatus() == Ticket.TicketStatus.RESERVED && !userEmail.equals(t.getOwnerEmail())) {
                System.out.println("Ticket No "+ticketId+" is already reserved");
                return false;
            }

            t.setStatus(Ticket.TicketStatus.SOLD);
            t.setOwnerEmail(userEmail);

            System.out.println("Ticket No "+ticketId+" successfully bought");
            return true;
        }
    }

    public boolean cancelReservation(int ticketId) {
        Ticket t = tickets.get(ticketId);
        if (t == null) throw new IllegalArgumentException("No ticket with that id");

        synchronized (t) {
            if (t.getStatus() == Ticket.TicketStatus.SOLD) {
                System.out.println("Ticket No "+ticketId+" is already sold");
                return false;
            }
            if (t.getStatus() != Ticket.TicketStatus.RESERVED) {
                System.out.println("Ticket No "+ticketId+" is not reserved");
                return false;
            }

            t.setStatus(Ticket.TicketStatus.AVAILABLE);
            t.setOwnerEmail(null);
            return true;
        }
    }

    public double getTotalRevenue() {
        return tickets.values().stream()
                .filter(t -> t.getStatus().equals(Ticket.TicketStatus.SOLD))
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    public Map<String, Integer> getEventStatistics() {
        return tickets.values().stream()
                .collect(Collectors.groupingBy(t -> t.getStatus().toString(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }

    public List<Ticket> getUserReservations(String userEmail) {
        return tickets.values().stream()
                .filter(t -> userEmail.equals(t.getOwnerEmail()))
                .toList();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tickets);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ConcurrentTicketService cts)) return false;

        return this.tickets.equals(cts.tickets);
    }

    @Override
    public String toString() {
        return tickets.values().stream()
                .map(Ticket::toString)
                .collect(Collectors.joining("\n"));
    }
}
