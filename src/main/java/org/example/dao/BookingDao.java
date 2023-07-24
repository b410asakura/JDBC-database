package org.example.dao;

import org.example.model.Booking;

import java.util.List;

public interface BookingDao {
    void saveBooking(Booking booking);

    Booking findById(Long bookingId);

    String delete(Long id);

    List<Booking> getAllBookings();

    List<Booking> getBookingByUserId(Long userId);
}
