package org.example.service;

import org.example.model.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovieService {
    String createMovie(String tableName, List<String> columns);

    String saveMovie(Movie movie);//save

    Movie findMovieById(Long id);

    List<Movie> searchByName(String title);

    List<Movie> sortByDuration(String ascOrDesc);

    List<Movie> getMoviesByTheaterIdAndStartTime(Long theatreId, LocalDateTime startTime);
    Map<String, List<Movie>> getMoviesByGenre(String genre);
}
