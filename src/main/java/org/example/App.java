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
        UserService userService=new UserServiceImpl();
        BookingService bookingService=new BookingServiceImpl();


//        System.out.println(movieService.createMovie(
//                "bookings",
//                List.of(
//                        "id serial primary key",
//                        "show_time_id int references show_time(id),"+
//                        "user_id int references users(id)," +
//                                "number_of_tickets int not null," +
//                                "booking_time timestamp")
//        ));
        Scanner scannerNum = new Scanner(System.in);
        Scanner scannerWord = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t~~~~~~~Choose operation~~~~~~~
                    Movie                              | User                                 |Booking                  |Show-time                         |Theatre                  
                    |1-save movie                      |7-save user                           |9-save booking           |14-save show_time                 |20-save theatre
                    |2-find movie by id                |8-check if exist user with given email|10-find booking by id    |15-assign showtime                |21-get all theatres
                    |3-search movie by name            |25-get all users                      |11-delete booking        |16-get all showtime               |22-update theatre by id
                    |4-get movies by genre             |26-update user                        |12-get all bookings      |17-find showtime by id            |23-delete theatre by id
                    |5-sort by duration                |27-delete user                        |13-get booking by user id|18-deleteShowTimeByStartAndEndTime|24-get all movies by time 
                    |6-getMoviesByTheaterIdAndStartTime|                                      |                         |19-getMoviesGroupByTheater        |
                    
                    """);
            switch (new Scanner(System.in).nextLine()) {
                case "1" -> {                                      //done
                    System.out.println("Write title");
                    String title = scannerWord.nextLine();
                    System.out.println("Write genre");
                    String genre = scannerWord.nextLine();
                    System.out.println("Write duration");
                    int duration = scannerNum.nextInt();
                    System.out.println(movieService.saveMovie(new Movie(title, genre, duration)));
                }
                case "2" -> {                                       //done
                    System.out.println("Write id of a movie you want to find: ");
                    System.out.println(movieService.findMovieById(scannerNum.nextLong()));
                }
                case "3"->{                                         //done
                    System.out.println("Input movie name(title) you want to search:");
                    String title=scannerWord.nextLine();
                    movieService.searchByName(title).forEach(System.out::println);
                }
                case "4"->{                                             //done
                    System.out.println("Input genre you want to get movies by");
                    String genre=scannerWord.nextLine();
                    System.out.println(movieService.getMoviesByGenre(genre));
                }
                case "5"->{                                         //done
                    System.out.println("Input asc or desc depending on what way you want to sort!");
                    String ascOrDesc=scannerWord.nextLine();
                    movieService.sortByDuration(ascOrDesc).forEach(System.out::println);
                }
                case "6"->{                                     //done
                    movieService.getMoviesByTheaterIdAndStartTime(3L,
                            LocalDateTime.of(2023, 7,20,18,30,0))
                            .forEach(System.out::println);
                }
                case "9"->{             //done
                    System.out.println("Input show_time_id: ");
                    Long showTimeId=scannerNum.nextLong();
                    System.out.println("Input userId: ");
                    Long userId=scannerNum.nextLong();
                    System.out.println("Input number of tickets: ");
                    int numberOfTickets=scannerNum.nextInt();
                    System.out.println(bookingService.saveBooking(new Booking(showTimeId, userId, numberOfTickets)));
                }
                case "10"->{                //done
                    System.out.println("Input booking id: ");
                    Long id=scannerNum.nextLong();
                    bookingService.findById(id);
                }
                case "11"->{
                    System.out.println("Input booking id you want to delete: ");
                    Long id=scannerNum.nextLong();
                    bookingService.deleteBookingById(id);
                }
                case "12"->{
                    bookingService.getAllBookings();
                }

                case "14" -> {                                                              //done
                    String save = showTimeService.save(new ShowTime(1L, 3L,
                            LocalDateTime.of(2024, 10, 4, 13, 40, 0),
                            LocalDateTime.of(2024, 9, 4, 16, 00, 0)
                    ));
                    System.out.println(save);
                }
                case "15" -> {                                                      //done
//                    System.out.println("Input show time id: ");
//                    Long showTimeId=scannerNum.nextLong();
//                    System.out.println("Input movie id: ");
//                    Long movieId=scannerNum.nextLong();
//                    System.out.println("Input theatre id: ");
//                    Long theatreId=scannerNum.nextLong();
//                    showTimeService.assign(showTimeId,movieId,theatreId);
                    showTimeService.assign(1L, 2L, 3L);
                }
                case "16"->showTimeService.getAll().forEach(System.out::println);   //done
                case "17"->{                                                        //done
                    System.out.println("Input show_time id you want to find:");
                    Long showTimeId=scannerNum.nextLong();
                    System.out.println(showTimeService.findById(showTimeId));
                }
                case "18"->{                         //done
                    System.out.println(showTimeService.deleteShowTimeByStartAndEndTime(
                            LocalDateTime.of(2024, 10, 4, 13, 40, 0),
                            LocalDateTime.of(2024, 9, 4, 16, 0, 0)
                    ));
                }
                case "19"->{
                    showTimeService.getMoviesGroupByTheater().forEach(System.out::println);

                }

                case "20" -> {                                          //20-23done
                    System.out.println("Input a name of a theatre: ");
                    String name=scannerWord.nextLine();
                    System.out.println("Input location of theatre: ");
                    String location=scannerWord.nextLine();
                    System.out.println(theatreService.saveTheatre(new Theatre(name, location)));
                }
                case "21"->theatreService.getAllTheatres().forEach(System.out::println);
                case "22"->{
                    System.out.println("Input old theatre_id you want to update: ");
                    Long id=scannerNum.nextLong();
                    System.out.println("Input new theatre's name: ");
                    String name=scannerWord.nextLine();
                    System.out.println("Input new theatre's location: ");
                    String location=scannerWord.nextLine();
                    theatreService.updateTheatre(id,new Theatre(name,location));
                }
                case "23"->{
                    System.out.println("Input theatre's id you want to delete: ");
                    Long id=scannerNum.nextLong();
                    System.out.println(theatreService.deleteTheatre(id));
                }
                case "24"->{
                    // параметирден канча саат берсек.
                    // Кинолордун узундугу ошол саатка барабар болгон бардык
                    // кинолорду жана театырларын чыгарышы керек!
                    System.out.println("Input duration in hours: ");
                    int hours=scannerNum.nextInt();
                    theatreService.getAllMoviesByTime(hours).forEach(System.out::println);
                }
                case "7"-> {         //done
                    System.out.println("Input username: ");
                    String userName = scannerWord.nextLine();
                    System.out.println("Input password: ");
                    String password = scannerWord.nextLine();
                    System.out.println("Input email: ");
                    String email = scannerWord.nextLine();
                    System.out.println(userService.saveUser(new User(userName, password, email)));

                }
                case "8"->{             //done
                    System.out.println("Input user's email to check for existing: ");
                    String email=scannerWord.nextLine();
                    System.out.println(userService.existsByEmail(email));

                }
                case "25"->userService.getAllUsers().forEach(System.out::println);//done
                case "26"->{                //done
                    System.out.println("Input user's id you want to update:");
                    Long id=scannerNum.nextLong();
                    System.out.println("Input new user's name: ");
                    String name=scannerWord.nextLine();
                    System.out.println("Input new user's password: ");
                    String password=scannerWord.nextLine();
                    System.out.println("Input new user's email: ");
                    String email=scannerWord.nextLine();
                    userService.updateUser(id,new User(name,password,email));
                }
                case "27"->{            //done
                    System.out.println("Input user's name you want to delete:");
                    String name=scannerWord.nextLine();
                    userService.deleteUser(name);
                }





            }
        }


    }
}

