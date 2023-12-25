package org.example.dao;

import org.example.model.Movie;
import org.example.model.Theatre;

import java.util.List;
import java.util.Map;

public interface TheatreDao {

    void createTable();
    Theatre findById(Long theatreId);

    void saveTheatre(Theatre theatre);

    List<Theatre> getAllTheatres();

    void updateTheatre(Long id, Theatre theatre);

    void deleteTheatre(Long id);

    List<Map<Movie, List<Theatre>>> getAllMoviesByTime(String hours);
}
