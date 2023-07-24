package org.example.service.impl;

import org.example.dao.BookingDao;
import org.example.dao.impl.BookingDaoImpl;
import org.example.model.Booking;
import org.example.service.BookingService;

public class BookingServiceImpl implements BookingService {
    BookingDao bookingDao = new BookingDaoImpl();

    @Override
    public String saveBooking(Booking booking) {
        bookingDao.saveBooking(booking);
        return "Successfully saved new booking!";
    }

    @Override
    public void findById(Long bookingId) {
        System.out.println(bookingDao.findById(bookingId));
    }

    @Override
    public void deleteBookingById(Long id) {
        System.out.println(bookingDao.delete(id));
    }

    @Override
    public void getAllBookings() {
        bookingDao.getAllBookings().forEach(System.out::println);
    }

    @Override
    public void getBookingByUserId(Long userId) {
        bookingDao.getBookingByUserId(userId).forEach(System.out::println);
    }

}
