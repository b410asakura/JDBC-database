package org.example;

import org.example.model.*;
import org.example.service.*;
import org.example.service.impl.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        MovieService movieService = new MovieServiceImpl();
        TheatreService theatreService = new TheatreServiceImpl();
        ShowTimeService showTimeService = new ShowTimeServiceImpl();
        UserService userService = new UserServiceImpl();
        BookingService bookingService = new BookingServiceImpl();
        Scanner scannerNum = new Scanner(System.in);
        Scanner scannerWord = new Scanner(System.in);

        ShowTime showTime = new ShowTime();
        System.out.println(showTime.getId());
        System.out.println();

        while (true) {
            System.out.println("""
                    \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t~~~~~~~Choose operation~~~~~~~
                    Movie                                              User                                                                   Booking                                              Show-time                                    Theatre        
                    |1-create table                          |2-create table                                               |3-create table                                   |4-create table                           |5-create table
                    |1.1-save movie                           |2.1-save user                                                  |3.1-save booking                                |4.1-save showtime                      |5.1-save theatre
                    |1.2-get all movies                     |2.2-get all users                                             |3.2-get all bookings                           |4.2-get all show times               |5.2-get all theatres
                    |1.3-find movie by id                 |2.3=find user by id                                         |3.3-find booking by user id               |4.3-find show time by id           |5.3-find by theatre id
                    |1.4-update movie                      |2.4-update user                                              |3.4-update booking                           |4.4-update show time                |5.4-update theatre
                    |1.5-delete movie                       |2.5-delete user                                              |3.5-delete booking                            |4.5-delete show time                 |5.5-delete theatre
                    |1.6-search movie by name        |2.6-check if exist user with given email       |3.6-get booking by user id               |4.6-get all movies in theatre      |5.6-get all movies by time 
                    |1.7-get movies by genre                                                                                                                                                                                                                                                                                                                                     
                    """);
            switch (new Scanner(System.in).nextLine()) {
                case "1" -> {
                    movieService.createTable();
                }
                case "1.1" -> {
                    System.out.println("Write title");
                    String title = scannerWord.nextLine();
                    System.out.println("Write genre");
                    String genre = scannerWord.nextLine();
                    System.out.println("Write duration (integer only)");
                    String duration = scannerWord.nextLine();
                    System.out.println(movieService.saveMovie(new Movie(title, genre, duration)));
                }
                case "1.2" -> {
                   movieService.getAll().forEach(System.out::println);
                }
                case "1.3" -> {
                    System.out.println("Write id of a movie you want to find: ");
                    System.out.println(movieService.findMovieById(scannerNum.nextLong()));
                }
                case "1.4" -> {
                    System.out.println("Input movie's id you want to update:");
                    Long id = scannerNum.nextLong();
                    System.out.println("Input new movie's title: ");
                    String title = scannerWord.nextLine();
                    System.out.println("Input new movie's genre: ");
                    String genre = scannerWord.nextLine();
                    System.out.println("Input new movie's duration: ");
                    String duration = scannerWord.nextLine();
                    movieService.update(id, new Movie(title, genre, duration));
                    System.out.println("Movie was updated");
                }
                case "1.5" -> {
                    System.out.println("Input movie's id you want to delete:");
                    Long id = scannerNum.nextLong();
                    movieService.delete(id);
                }
                case "1.6" -> {
                    System.out.println("Input movie name(title) you want to search:");
                    String title = scannerWord.nextLine();
                    movieService.searchByName(title).forEach(System.out::println);
                }
                case "1.7" -> {
                    System.out.println("Input genre you want to get movies by");
                    String genre = scannerWord.nextLine();
                    System.out.println(movieService.getMoviesByGenre(genre));
                }
                case "2" -> {
                    userService.createTable();
                }
                case "2.1" -> {
                    System.out.println("Input username: ");
                    String userName = scannerWord.nextLine();
                    System.out.println("Input password: ");
                    String password = scannerWord.nextLine();
                    System.out.println("Input email: ");
                    String email = scannerWord.nextLine();
                    System.out.println(userService.saveUser(new User(userName, password, email)));
                }
                case "2.2" ->{
                    userService.getAllUsers().forEach(System.out::println);
                }
                case "2.3" -> {
                    System.out.println("Input user's id you want to get");
                    System.out.println(userService.getById(scannerNum.nextLong()));
                }
                case "2.4" -> {
                    System.out.println("Input user's id you want to update:");
                    Long id = scannerNum.nextLong();
                    System.out.println("Input new user's name: ");
                    String name = scannerWord.nextLine();
                    System.out.println("Input new user's password: ");
                    String password = scannerWord.nextLine();
                    System.out.println("Input new user's email: ");
                    String email = scannerWord.nextLine();
                    userService.updateUser(id, new User(name, password, email));
                }
                case "2.5" -> {
                    System.out.println("Input user's name you want to delete:");
                    String name = scannerWord.nextLine();
                    userService.deleteUser(name);
                }
                case "2.6" -> {
                    System.out.println("Input user's email to check for existing: ");
                    String email = scannerWord.nextLine();
                    System.out.println(userService.existsByEmail(email));
                }
                case "3" -> {
                    bookingService.createTable();
                }
                case "3.1" -> {
                    System.out.println("Input show_time_id: ");
                    Long showTimeId = scannerNum.nextLong();
                    System.out.println("Input userId: ");
                    Long userId = scannerNum.nextLong();
                    System.out.println("Input number of tickets: ");
                    int numberOfTickets = scannerNum.nextInt();
                    System.out.println("Input booking time");
                    String bookingTime = scannerWord.next();
                    System.out.println(bookingService.saveBooking(new Booking(showTimeId, userId, numberOfTickets, bookingTime)));
                }
                case "3.2" -> {
                    bookingService.getAllBookings();
                }
                case "3.3" -> {
                    System.out.println("Input booking id: ");
                    Long id = scannerNum.nextLong();
                    bookingService.findById(id);
                }
                case "3.4" -> {
                    System.out.println("Input booking's id you want to update:");
                    Long id = scannerNum.nextLong();
                    System.out.println("Input new user id:");
                    Long userId = scannerNum.nextLong();
                    System.out.println("Input new show time id:");
                    Long showTimeId = scannerNum.nextLong();
                    System.out.println("Input new booking time: ");
                    String bookingTime = scannerWord.nextLine();
                    System.out.println("Input new number of tickets");
                    int numberOfTickets = scannerNum.nextInt();
                    bookingService.updateBooking(id, new Booking(showTimeId, userId, numberOfTickets, bookingTime));
                }
                case "3.5" -> {
                    System.out.println("Input booking id you want to delete: ");
                    Long id = scannerNum.nextLong();
                    bookingService.deleteBookingById(id);
                }
                case "3.6" -> {
                    System.out.println("Input user id to get booking: ");
                    Long userId = scannerNum.nextLong();
                    bookingService.getBookingByUserId(userId);
                }
                case "4" -> {
                    showTimeService.createTable();
                }
                case "4.1" -> {
                    System.out.println("Input movie_id: ");
                    Long movieId = scannerNum.nextLong();
                    System.out.println("Input theatre_id: ");
                    Long theatreId = scannerNum.nextLong();
                    System.out.println("Input start time: ");
                    String startTime = scannerWord.nextLine();
                    System.out.println("Input end time: ");
                    String endTime = scannerWord.nextLine();
                    System.out.println(showTimeService.save(new ShowTime(movieId, theatreId, startTime, endTime)));
                }
                case "4.2" -> {
                    showTimeService.getAll().forEach(System.out::println);
                }
                case "4.3" -> {
                    System.out.println("Input show_time id you want to find:");
                    Long showTimeId = scannerNum.nextLong();
                    System.out.println(showTimeService.findById(showTimeId));
                }
                case "4.4" -> {
                    System.out.println("Input show time id: ");
                    Long showTimeId=scannerNum.nextLong();
                    System.out.println("Input movie id: ");
                    Long movieId=scannerNum.nextLong();
                    System.out.println("Input theatre id: ");
                    Long theatreId=scannerNum.nextLong();
                    System.out.println("Input new start time");
                    String startTime = scannerNum.next();
                    System.out.println("Input end start time");
                    String endTime = scannerNum.next();
                    showTimeService.update(showTimeId, new ShowTime(movieId, theatreId, startTime, endTime));
                }
                case "4.5" -> {
                    System.out.println("Input show time id: you want to delete ");
                    Long showTimeId=scannerNum.nextLong();
                    System.out.println(showTimeService.deleteShowTime(showTimeId));
                }
                case "4.6" -> {
                    showTimeService.getMoviesGroupByTheater().forEach(System.out::println);//done
                }
                case "5" -> {
                    theatreService.createTable();
                }
                case "5.1" -> {
                    System.out.println("Input a name of a theatre: ");
                    String name = scannerWord.nextLine();
                    System.out.println("Input location of theatre: ");
                    String location = scannerWord.nextLine();
                    System.out.println(theatreService.saveTheatre(new Theatre(name, location)));
                }
                case "5.2" -> {
                    theatreService.getAllTheatres().forEach(System.out::println);
                }
                case "5.3" -> {
                    System.out.println("Input theatre's id you want to get: ");
                    Long theatreId = scannerNum.nextLong();
                    System.out.println(theatreService.findById(theatreId));
                }
                case "5.4" -> {
                    System.out.println("Input old theatre_id you want to update: ");
                    Long id = scannerNum.nextLong();
                    System.out.println("Input new theatre's name: ");
                    String name = scannerWord.nextLine();
                    System.out.println("Input new theatre's location: ");
                    String location = scannerWord.nextLine();
                    theatreService.updateTheatre(id, new Theatre(name, location));
                }
                case "5.5" -> {
                    System.out.println("Input theatre's id you want to delete: ");
                    Long id = scannerNum.nextLong();
                    System.out.println(theatreService.deleteTheatre(id));
                }
                case "5.6" -> {
                    System.out.println("Input duration in minutes: ");
                    String hours = scannerNum.nextLine();
                    theatreService.getAllMoviesByTime(hours).forEach(System.out::println);
                }
            }
        }
    }
}
