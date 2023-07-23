package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.MovieDao;
import org.example.model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDaoImpl implements MovieDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public void createTable(String tableName, List<String> columns) {
        StringBuilder stringBuilder =
                new StringBuilder(String.format("create table %s (", tableName));
        try {
            Statement statement = connection.createStatement();
            for (int i = 0; i < columns.size(); i++) {
                stringBuilder.append(columns.get(i));
                if (i < columns.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");
            statement.executeUpdate(stringBuilder.toString());
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(Movie movie) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                """
                                            insert into movies(title,genre,duration)
                                            values (?,?,?)
                        """
        );) {

            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setInt(3, movie.getDuration());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Movie findById(Long id) {
        Movie movie = new Movie();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                                            select * from movies where id=?
                            """);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Movie with id :" + id + " not found!");
            } else {
                movie.setId(resultSet.getLong("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setGenre(resultSet.getString("genre"));
                movie.setDuration(resultSet.getInt("duration"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }

    @Override
    public List<Movie> searchByName(String title) {
        List<Movie> movies = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("""
                    select * from movies where title ilike ?
                                    """);
            preparedStatement.setString(1, title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                movies.add(
                        new Movie(resultSet.getLong("id"),
                                resultSet.getString("title"),
                                resultSet.getString("genre"),
                                resultSet.getInt("duration")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Movie with title " + title + " not found!!!");
        }
        return movies;
    }

    @Override
    public List<Movie> sortByDuration(String ascOrDesc) {
        List<Movie> movies = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from movies order by duration ");
        if (ascOrDesc.equalsIgnoreCase("asc")) sql.append(ascOrDesc);
        else if (ascOrDesc.equalsIgnoreCase("desc")) sql.append(ascOrDesc);
        else throw new RuntimeException("Wrong input!");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                movies.add(new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    @Override
    public List<Movie> getMoviesByTheaterIdAndStartTime(Long theatreId, LocalDateTime startTime) {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select m.* from show_time as st 
                    join movies as m on st.movie_id=m.id where st.theatre_id=? and st.start_time=?;
                                        
                    """);
            preparedStatement.setLong(1, theatreId);
            preparedStatement.setTimestamp(2,Timestamp.valueOf(startTime));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                movies.add(new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    @Override
    public Map<String, List<Movie>> getMoviesByGenre(String genre) {
        Map<String, List<Movie>> map=new HashMap<>();
        List<Movie>movies=new ArrayList<>();
       try( PreparedStatement preparedStatement = connection.prepareStatement("""
                select * from movies where genre like ?                
                """);){
           preparedStatement.setString(1,genre+"%");
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()){
               movies.add(new Movie(
                       resultSet.getLong("id"),
                       resultSet.getString("title"),
                       resultSet.getString("genre"),
                       resultSet.getInt("duration")
               ));
           }
           map.put(genre,movies);
       }catch (SQLException e){
           throw new RuntimeException(e);
       }
        return map;
    }
}


