package org.example.dao;

import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;

import java.util.List;
import java.util.Map;

public interface ShowTimeDao{

    void createTable();
    ShowTime save(ShowTime showTime);

    ShowTime findById(Long showTimeId);

    List<ShowTime> getAll();

    String deleteShowTime(Long id);
    List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater();

    void update(Long showTimeId, ShowTime showTime);
}
