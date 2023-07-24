package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.TheatreDao;
import org.example.model.Movie;
import org.example.model.Theatre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreDaoImpl implements TheatreDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public Theatre findById(Long theatreId) {
        Theatre theatre = new Theatre();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select * from theatres where id=?");
            preparedStatement.setLong(1, theatreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                theatre.setId(resultSet.getLong("id"));
                theatre.setName(resultSet.getString("name"));
                theatre.setLocation(resultSet.getString("location"));
            } else {
                throw new RuntimeException(String.format("Theatre with id: %d not found!", theatreId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return theatre;
    }

    @Override
    public void saveTheatre(Theatre theatre) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                """
                                            insert into theatres(name,location)
                                            values (?,?)
                        """
        )) {
            preparedStatement.setString(1, theatre.getName());
            preparedStatement.setString(2, theatre.getLocation());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Theatre> getAllTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("""
                                select * from theatres
                    """);
            while (resultSet.next()) {
                theatres.add(new Theatre(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return theatres;
    }

    @Override
    public void updateTheatre(Long id, Theatre theatre) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                update theatres set name=?,location=? where id=?
                """);) {
            preparedStatement.setString(1, theatre.getName());
            preparedStatement.setString(2, theatre.getLocation());
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();
            System.out.println("Successfully updated theatre with id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTheatre(Long id) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("""
                                        delete from theatres where id=?
                        """);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<Movie, List<Theatre>>> getAllMoviesByTime(int hours) {
        // параметирден канча саат берсек.
        // Кинолордун узундугу ошол саатка барабар болгон бардык
        // кинолорду жана театырларын чыгарышы керек!
        Movie movie;
        List<Theatre> theatres;
        Map<Movie, List<Theatre>> mapOfMovieAndTheatres = new HashMap<>();
        List<Map<Movie, List<Theatre>>> finalList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                select m.*,t.* from show_time
                join movies m on show_time.movie_id = m.id
                join theatres t on show_time.theatre_id = t.id
                where duration=?;                    
                """)) {
            preparedStatement.setInt(1, hours);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                movie = new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                );
                theatres = new ArrayList<>(List.of(new Theatre(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location")
                )));
                mapOfMovieAndTheatres.put(movie, theatres);
            }
            finalList.add(mapOfMovieAndTheatres);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return finalList;
    }
}



