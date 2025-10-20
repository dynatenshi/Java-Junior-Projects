package TicketOrderSystem.classes;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    public enum TicketStatus {
        AVAILABLE,
        RESERVED,
        SOLD
    }

    private final double MIN_PRICE = 1;
    private final double MAX_PRICE = 1_000_000;

    private final int id;
    private final String eventName;
    private final LocalDateTime eventDate;
    private final double price;
    private volatile TicketStatus status;
    private volatile String ownerEmail;

    public Ticket(int id, String eventName, LocalDateTime eventDate, double price) {
        this.id = validateId(id);
        this.eventName = validateEventName(eventName);
        this.eventDate = validateEventDate(eventDate);
        this.price = validatePrice(price);
        status = TicketStatus.AVAILABLE;
        ownerEmail = null;
    }

    private int validateId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be greater than 0");
        return id;
    }
    private String validateEventName(String eventName) {
        return eventName;
    }
    private LocalDateTime validateEventDate(LocalDateTime eventDate) {
        return eventDate;
    }
    private double validatePrice(double price) {
        if (price < MIN_PRICE) throw new IllegalArgumentException("Price must be greater than "+MIN_PRICE);
        if (price > MAX_PRICE) throw new IllegalArgumentException("Price must be less than "+MAX_PRICE);
        return price;
    }

    public int getId() {
        return id;
    }
    public String getEventName() {
        return eventName;
    }
    public LocalDateTime getEventDate() {
        return eventDate;
    }
    public double getPrice() {
        return price;
    }
    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    public String getOwnerEmail() {
        return ownerEmail;
    }
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, eventDate, price, status, ownerEmail);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ticket t)) return false;

        return this.id == t.getId();
    }

    @Override
    public String toString() {
        return String.format("id:%d | event:%s | price:%.2f | status:%s | owner:%s",
                id, eventName, price, status.name().toLowerCase(), ownerEmail);
    }
}
