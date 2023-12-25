package org.example.service;

import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;

import java.util.List;
import java.util.Map;

public interface ShowTimeService {
    void createTable();
    String save(ShowTime showTime);

    List<ShowTime> getAll();

    ShowTime findById(Long showTimeId);

    String deleteShowTime(Long id);

    List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater();

    void update(Long showTimeId, ShowTime showTime);
}
