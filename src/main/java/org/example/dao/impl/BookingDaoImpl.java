package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.BookingDao;
import org.example.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE bookings (
                      id SERIAL PRIMARY KEY,
                      showtime_id INT REFERENCES show_time(id),
                      user_id INT REFERENCES users(id),
                      number_of_tickets INT NOT NULL,
                      booking_time VARCHAR(255) NOT NULL
                  )
                  """;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("table bookings created");
    }

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
            preparedStatement.setString(4, booking.getBookingTime());
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
                """)) {
            preparedStatement.setLong(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Not found booking with id=" + bookingId);
            } else {
                booking.setId(resultSet.getLong("id"));
                booking.setShowTimeId(resultSet.getLong("show_time_id"));
                booking.setUserId(resultSet.getLong("user_id"));
                booking.setNumberOfTickets(resultSet.getInt("number_of_tickets"));
                booking.setBookingTime(resultSet.getString("booking_time"));
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
                """)) {
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
                        resultSet.getString("booking_time"))
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
                       resultSet.getString("booking_time"))
               );
           }
       }catch (SQLException e){
           throw new RuntimeException("not found user id!");
       }
        return bookings;
    }

    @Override
    public void update(Long id, Booking booking) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                update bookings set user_id=?,show_time_id=?,number_of_tickets=?,booking_time=? where id=?
                """)) {
            preparedStatement.setLong(1, booking.getUserId());
            preparedStatement.setLong(2, booking.getShowTimeId());
            preparedStatement.setInt(3, booking.getNumberOfTickets());
            preparedStatement.setString(4, booking.getBookingTime());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println( "Successfully updated booking with id=" + id);
    }
}
