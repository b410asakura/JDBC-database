package org.example.service;

import org.example.model.Movie;
import org.example.model.Theatre;

import java.util.List;
import java.util.Map;

public interface TheatreService {
    void createTable();
    String saveTheatre(Theatre theatre);

    List<Theatre> getAllTheatres();

    void updateTheatre(Long id, Theatre theatre);

    String deleteTheatre(Long id);

    List<Map<Movie, List<Theatre>>> getAllMoviesByTime(String hours);

    Theatre findById(Long theatreId);
}
