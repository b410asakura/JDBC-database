package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.BookingDao;
import org.example.model.Booking;

import java.io.ObjectInputFilter;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public void saveBooking(Booking booking) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into bookings(
                    show_time_id,
                    user_id,
                    number_of_tickets,
                    booking_time
                    )values (
                    ?,?,?,?
                    )      
                    """);
            preparedStatement.setLong(1, booking.getShowTimeId());
            preparedStatement.setLong(2, booking.getUserId());
            preparedStatement.setInt(3, booking.getNumberOfTickets());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Booking findById(Long bookingId) {
        Booking booking = new Booking();
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                select * from bookings where id=?        
                """);) {
            preparedStatement.setLong(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Not found booking with id=" + bookingId);
            } else {
                booking.setId(resultSet.getLong("id"));
                booking.setShowTimeId(resultSet.getLong("show_time_id"));
                booking.setUserId(resultSet.getLong("user_id"));
                booking.setNumberOfTickets(resultSet.getInt("number_of_tickets"));
                booking.setBookingTime(resultSet.getTimestamp("booking_time").toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    @Override
    public String delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                delete from bookings where id=?                
                """);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Booking with id " + id + " is successfully deleted! ";
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("""
                     select * from bookings
                     """);) {
            while(resultSet.next()){
                bookings.add(new Booking(
                        resultSet.getLong("id"),
                        resultSet.getLong("show_time_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("number_of_tickets"),
                        resultSet.getTimestamp("booking_time").toLocalDateTime())
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingByUserId(Long userId) {
        List<Booking>bookings=new ArrayList<>();
       try( PreparedStatement preparedStatement = connection.prepareStatement("""
                                select * from bookings where user_id=?
                """);){
           preparedStatement.setLong(1,userId);
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()){
               bookings.add(new Booking(
                       resultSet.getLong("id"),
                       resultSet.getLong("show_time_id"),
                       resultSet.getLong("user_id"),
                       resultSet.getInt("number_of_tickets"),
                       resultSet.getTimestamp("booking_time").toLocalDateTime())
               );
           }
       }catch (SQLException e){
           throw new RuntimeException("not found user id!");
       }
        return bookings;
    }
}
