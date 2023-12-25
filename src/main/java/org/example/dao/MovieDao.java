package org.example.dao;

import org.example.model.Movie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovieDao {
    void createTable();

    void save(Movie movie);

    Movie findById(Long id);
    List<Movie> searchByName(String title);

    List<Movie> sortByDuration(String ascOrDesc);

    List<Movie> getMoviesByTheaterIdAndStartTime(Long theatreId, LocalDateTime startTime);

    Map<String, List<Movie>> getMoviesByGenre(String genre);

    List<Movie> getAll();

    void update(Long id, Movie movie);

    void delete(Long id);
}
