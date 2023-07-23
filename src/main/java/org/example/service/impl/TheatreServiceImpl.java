package org.example.service.impl;

import org.example.dao.TheatreDao;
import org.example.dao.impl.TheatreDaoImpl;
import org.example.model.Movie;
import org.example.model.Theatre;
import org.example.service.TheatreService;

import java.util.List;
import java.util.Map;

public class TheatreServiceImpl implements TheatreService {

    TheatreDao theatreDao=new TheatreDaoImpl();

    @Override
    public String saveTheatre(Theatre theatre) {
        theatreDao.saveTheatre(theatre);
        return "Theatre is successfully saved!";
    }

    @Override
    public List<Theatre> getAllTheatres() {
        return theatreDao.getAllTheatres();
    }

    @Override
    public void updateTheatre(Long id, Theatre theatre) {
        theatreDao.updateTheatre(id,theatre);
    }

    @Override
    public String deleteTheatre(Long id) {
        theatreDao.deleteTheatre(id);
        return "Successfully deleted theatre with id="+id;
    }

    @Override
    public List<Map<Movie, List<Theatre>>> getAllMoviesByTime(int hours) {
        return theatreDao.getAllMoviesByTime(hours);
    }
}
