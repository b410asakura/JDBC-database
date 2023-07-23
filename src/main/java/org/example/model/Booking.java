package org.example.model;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private Long showTimeId;
    private Long userId;
    private int numberOfTickets;
    private LocalDateTime bookingTime;

    public Booking() {
    }

    public Booking(Long id, Long showTimeId, Long userId, int numberOfTickets, LocalDateTime bookingTime) {
        this.id = id;
        this.showTimeId = showTimeId;
        this.userId = userId;
        this.numberOfTickets = numberOfTickets;
        this.bookingTime = bookingTime;
    }

    public Booking(Long showTimeId, Long userId, int numberOfTickets, LocalDateTime bookingTime) {
        this.showTimeId = showTimeId;
        this.userId = userId;
        this.numberOfTickets = numberOfTickets;
        this.bookingTime = bookingTime;
    }

    public Booking(Long showTimeId, Long userId, int numberOfTickets) {
        this.showTimeId = showTimeId;
        this.userId = userId;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(Long showTimeId) {
        this.showTimeId = showTimeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", showTimeId=" + showTimeId +
                ", userId=" + userId +
                ", numberOfTickets=" + numberOfTickets +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
