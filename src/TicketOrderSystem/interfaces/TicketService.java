package TicketOrderSystem.interfaces;

import TicketOrderSystem.classes.Ticket;
import java.util.List;
import java.util.Map;

public interface TicketService {
    List<Ticket> getAllTickets();
    List<Ticket> getAvailableTickets();
    int getAvailableTicketCount();
    boolean reserveTicket(int ticketId, String userEmail);
    boolean purchaseTicket(int ticketId, String userEmail);
    boolean cancelReservation(int ticketId);
    double getTotalRevenue();
    Map<String, Integer> getEventStatistics();
    List<Ticket> getUserReservations(String userEmail);
}
