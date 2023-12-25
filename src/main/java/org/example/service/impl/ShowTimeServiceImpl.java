package org.example.service.impl;

import org.example.dao.MovieDao;
import org.example.dao.ShowTimeDao;
import org.example.dao.TheatreDao;
import org.example.dao.impl.MovieDaoImpl;
import org.example.dao.impl.ShowTimeDaoImpl;
import org.example.dao.impl.TheatreDaoImpl;
import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;
import org.example.service.ShowTimeService;

import java.util.List;
import java.util.Map;

public class ShowTimeServiceImpl implements ShowTimeService {
    ShowTimeDao showTimeDao=new ShowTimeDaoImpl();
    TheatreDao theatreDao=new TheatreDaoImpl();
    MovieDao movieDao=new MovieDaoImpl();

    @Override
    public void createTable() {
        showTimeDao.createTable();
    }

    @Override
    public String save(ShowTime showTime) {
        theatreDao.findById(showTime.getTheatreId());
        movieDao.findById(showTime.getMovieId());
        ShowTime timeMovie = showTimeDao.save(showTime);
        return "Successfully saved with show-time: "+timeMovie.toString();
    }


    @Override
    public List<ShowTime> getAll() {
        return showTimeDao.getAll();
    }

    @Override
    public ShowTime findById(Long showTimeId) {
        return showTimeDao.findById(showTimeId);
    }

    @Override
    public String deleteShowTime(Long id) {
        return showTimeDao.deleteShowTime(id);
    }

    @Override
    public List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater() {
        return showTimeDao.getMoviesGroupByTheater();
    }

    @Override
    public void update(Long showTimeId, ShowTime showTime) {
        showTimeDao.update(showTimeId, showTime);
    }
}
