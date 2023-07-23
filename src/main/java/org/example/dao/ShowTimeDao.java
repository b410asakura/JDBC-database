package org.example.dao;

import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ShowTimeDao{
    ShowTime save(ShowTime showTime);

    ShowTime findById(Long showTimeId);

    void assign(ShowTime showTime);

    List<ShowTime> getAll();

    String deleteShowTimeByStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime);
    List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater();
}
