package org.example.model;

import java.time.LocalDateTime;

public class ShowTime {
    private Long id;
    private Long movieId;
    private Long theatreId;
    private String startTime;
    private String endTime;

    public ShowTime() {
    }
    public ShowTime(Long id, Long movieId, Long theatreId, String startTime, String endTime) {
        this.id = id;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShowTime(Long movieId, Long theatreId, String startTime, String endTime) {
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShowTime(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String    endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ShowTime{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", theatreId=" + theatreId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
