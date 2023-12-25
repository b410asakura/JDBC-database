package org.example.service.impl;

import org.example.dao.MovieDao;
import org.example.dao.impl.MovieDaoImpl;
import org.example.model.Movie;
import org.example.service.MovieService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MovieServiceImpl implements MovieService {
    MovieDao movieDao = new MovieDaoImpl();


    @Override
    public void createTable() {
        movieDao.createTable();
    }

    @Override
    public String saveMovie(Movie movie) {
        movieDao.save(movie);
        return "Saved success";
    }

    @Override
    public Movie findMovieById(Long id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> searchByName(String title) {
        return movieDao.searchByName(title);
    }

    @Override
    public List<Movie> sortByDuration(String ascOrDesc) {
        return movieDao.sortByDuration(ascOrDesc);
    }

    @Override
    public List<Movie> getMoviesByTheaterIdAndStartTime(Long theatreId, LocalDateTime startTime) {
        return movieDao.getMoviesByTheaterIdAndStartTime(theatreId, startTime);
    }

    @Override
    public Map<String, List<Movie>> getMoviesByGenre(String genre) {
        return movieDao.getMoviesByGenre(genre);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public void update(Long id, Movie movie) {
        movieDao.update(id, movie);
    }

    @Override
    public void delete(Long id) {
        movieDao.delete(id);
    }
}
