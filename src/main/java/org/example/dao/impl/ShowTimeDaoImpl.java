package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.ShowTimeDao;
import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class ShowTimeDaoImpl implements ShowTimeDao {
    private Connection connection = JdbcConfig.getConnection();

    @Override
    public ShowTime save(ShowTime showTime) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "insert into show_time(movie_id,theatre_id,start_time,end_time)" +
                    "values(?,?,?,?)");
            preparedStatement.setLong(1, showTime.getMovieId());
            preparedStatement.setLong(2, showTime.getTheatreId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(showTime.getStartTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(showTime.getEndTime()));
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getShowTimeFindStartAndEnd(showTime.getStartTime(), showTime.getEndTime());
    }

    @Override
    public ShowTime findById(Long showTimeId) {
        ShowTime showTime = new ShowTime();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from show_time where id = ?
                    """);
            preparedStatement.setLong(1, showTimeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Show time with id : " + showTimeId + " not found!");
            } else {
                showTime.setId(resultSet.getLong("id"));
                showTime.setMovieId(resultSet.getLong("movie_id"));
                showTime.setTheatreId(resultSet.getLong("theatre_id"));
                showTime.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                showTime.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return showTime;
    }

    @Override
    public void assign(ShowTime showTime) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                                        update show_time set
                                        movie_id=?,
                                        theatre_id=?
                                        where id=?
                    """);
            preparedStatement.setLong(1, showTime.getMovieId());
            preparedStatement.setLong(2, showTime.getTheatreId());
            preparedStatement.setLong(3, showTime.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShowTime> getAll() {
        List<ShowTime> showTimes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("""
                                 select * from show_time
                     """);) {
            while (resultSet.next()) {
                showTimes.add(new ShowTime(
                        resultSet.getLong("id"),
                        resultSet.getLong("movie_id"),
                        resultSet.getLong("theatre_id"),
                        resultSet.getTimestamp("start_time").toLocalDateTime(),
                        resultSet.getTimestamp("end_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showTimes;
    }

    @Override
    public String deleteShowTimeByStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                            delete from show_time where start_time=? and end_time=?
                                
                """);) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endTime));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Successfully deleted show_time with start_time = " + startTime + " and end_time =" + endTime;
    }

    @Override
    public List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater() {
//        List<Movie> movies = new ArrayList<>();
//        Theatre theatre = new Theatre();
//        Map<Theatre, List<Movie>> map = new HashMap<>();
//        List<Map<Theatre, List<Movie>>> list = new ArrayList<>();
//        try (
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery("""
//                        select t.*,m.* from show_time as st
//                        join movies m on st.movie_id = m.id
//                        join theatres t on st.theatre_id = t.id group by m.id,t.id order by t.id;
//                        """);) {
//            if(resultSet.next()){
//                theatre=new Theatre(
//                        resultSet.getLong("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("location")
//                );
//            }
//            while (resultSet.next()){
//                movies.add(new Movie(
//                        resultSet.getLong("id"),
//                        resultSet.getString("title"),
//                        resultSet.getString("genre"),
//                        resultSet.getInt("duration")
//                ));
//
//            }
//            map.put(theatre,movies);
//            list.add(map);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
        List<Movie> movies = new ArrayList<>();
        Theatre theatre = new Theatre();
        Map<Theatre, List<Movie>> map = new HashMap<>();
        List<Map<Theatre, List<Movie>>> list = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                        select t.*,m.* from show_time as st
                        join movies m on st.movie_id = m.id
                        join theatres t on st.theatre_id = t.id group by t.id,m.id order by t.id;
                        """);){
            while (resultSet.next()){
                theatre=new Theatre(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location")
                );
                movies=map.getOrDefault(theatre,new ArrayList<>());
                movies.add(new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                ));
                map.put(theatre,movies);
            }
            list.add(map);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }
    public ShowTime getShowTimeFindStartAndEnd(LocalDateTime start_time, LocalDateTime end_time) {
        ShowTime showTime = new ShowTime();
        PreparedStatement StatementShowTime = null;
        try {
            StatementShowTime = connection.prepareStatement("""
                        select * from show_time where start_time=? and end_time=?
                    """);
            StatementShowTime.setTimestamp(1, Timestamp.valueOf(start_time));
            StatementShowTime.setTimestamp(2, Timestamp.valueOf(end_time));
            ResultSet resultSet = StatementShowTime.executeQuery();
            if (resultSet.next()) {
                showTime.setId(resultSet.getLong("id"));
                showTime.setMovieId(resultSet.getLong("movie_id"));
                showTime.setTheatreId(resultSet.getLong("theatre_id"));
                showTime.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                showTime.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());

            } else {
                throw new RuntimeException("Show time not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showTime;
    }
}
