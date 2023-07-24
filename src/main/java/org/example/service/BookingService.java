package org.example.service;

import org.example.model.Booking;

public interface BookingService {
    String saveBooking(Booking booking);

    void findById(Long bookingId);


    void deleteBookingById(Long id);

    void getAllBookings();

    void getBookingByUserId(Long userId);
}
