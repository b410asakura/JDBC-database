package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.ShowTimeDao;
import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTimeDaoImpl implements ShowTimeDao {
    private Connection connection = JdbcConfig.getConnection();

    @Override
    public void createTable() {
        String sql = """   
                CREATE TABLE show_time (
                                        id SERIAL PRIMARY KEY,
                                        movie_id INT REFERENCES movies(id),
                                        theatre_id INT REFERENCES theatres(id),
                                        start_time VARCHAR(255) NOT NULL,
                                        end_time VARCHAR(255) NOT NULL
                                    )
                                    """;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("table show_time created");
    }

    @Override
    public ShowTime save(ShowTime showTime) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "insert into show_time(movie_id,theatre_id,start_time,end_time)" +
                    "values(?,?,?,?)");
            preparedStatement.setLong(1, showTime.getMovieId());
            preparedStatement.setLong(2, showTime.getTheatreId());
            preparedStatement.setString(3, showTime.getStartTime());
            preparedStatement.setString(4, showTime.getEndTime());
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
                showTime.setStartTime(resultSet.getString("start_time"));
                showTime.setEndTime(resultSet.getString("end_time"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return showTime;
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
                        resultSet.getString("start_time"),
                        resultSet.getString("end_time")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showTimes;
    }

    @Override
    public String deleteShowTime(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                            delete from show_time where id = ?
                                
                """);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Successfully deleted show_time with id" + id;
    }

    @Override
    public List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater() {
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
                        resultSet.getString("duration")
                ));
                map.put(theatre,movies);
            }
            list.add(map);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(Long showTimeId, ShowTime showTime) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                                        update show_time set
                                        movie_id=?,
                                        theatre_id=?,
                                        start_time=?,
                                        end_time=?
                                        where id=?
                    """);
            preparedStatement.setLong(1, showTime.getMovieId());
            preparedStatement.setLong(2, showTime.getTheatreId());
            preparedStatement.setString(3, showTime.getStartTime());
            preparedStatement.setString(4, showTime.getEndTime());
            preparedStatement.setLong(5, showTimeId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ShowTime getShowTimeFindStartAndEnd(String start_time, String end_time) {
        ShowTime showTime = new ShowTime();
        PreparedStatement StatementShowTime = null;
        try {
            StatementShowTime = connection.prepareStatement("""
                        select * from show_time where start_time=? and end_time=?
                    """);
            StatementShowTime.setString(1, start_time);
            StatementShowTime.setString(2, end_time);
            ResultSet resultSet = StatementShowTime.executeQuery();
            if (resultSet.next()) {
                showTime.setId(resultSet.getLong("id"));
                showTime.setMovieId(resultSet.getLong("movie_id"));
                showTime.setTheatreId(resultSet.getLong("theatre_id"));
                showTime.setStartTime(resultSet.getString("start_time"));
                showTime.setEndTime(resultSet.getString("end_time"));

            } else {
                throw new RuntimeException("Show time not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showTime;
    }
}
