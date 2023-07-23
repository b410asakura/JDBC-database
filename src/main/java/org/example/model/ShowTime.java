package org.example.model;

import java.time.LocalDateTime;

public class ShowTime {
    private Long id;
    private Long movieId;
    private Long theatreId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ShowTime() {
    }
    public ShowTime(Long id, Long movieId, Long theatreId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShowTime(Long movieId, Long theatreId, LocalDateTime startTime, LocalDateTime endTime) {
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShowTime(LocalDateTime startTime, LocalDateTime endTime) {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
