package br.com.mascenadev.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodes {

    private Integer season;
    private String title;
    private Integer numberEpisode;
    private Double rating;
    private LocalDate released;

    public Episodes(Integer numberSeason, DataEpisodes dataEpisodes) {
        this.season = numberSeason;
        this.title = dataEpisodes.title();
        this.numberEpisode = dataEpisodes.numberEpisode();

        try {
            this.rating = Double.parseDouble(dataEpisodes.rating());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        }

        try {
            this.released = LocalDate.parse(dataEpisodes.released());
        } catch (DateTimeParseException ex) {
            this.released = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberEpisode() {
        return numberEpisode;
    }

    public void setNumberEpisode(Integer numberEpisode) {
        this.numberEpisode = numberEpisode;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return  "season=" + season +
                ", title='" + title + '\'' +
                ", numberEpisode=" + numberEpisode +
                ", rating=" + rating +
                ", released=" + released;
    }
}
