package br.com.mascenadev.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer season;
    private String title;
    private Integer numberEpisode;
    private Double rating;
    private LocalDate released;

    @ManyToOne
    private Serie serie;

    public Episodes() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
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
        return "season=" + season +
                ", title='" + title + '\'' +
                ", numberEpisode=" + numberEpisode +
                ", rating=" + rating +
                ", released=" + released;
    }
}
