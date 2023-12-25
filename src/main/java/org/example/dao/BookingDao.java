package org.example.dao;

import org.example.model.Booking;

import java.util.List;

public interface BookingDao {

    void createTable();
    void saveBooking(Booking booking);

    Booking findById(Long bookingId);

    String delete(Long id);

    List<Booking> getAllBookings();

    List<Booking> getBookingByUserId(Long userId);

    void update(Long id, Booking booking);
}
