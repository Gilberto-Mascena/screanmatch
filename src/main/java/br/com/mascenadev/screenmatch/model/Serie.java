package br.com.mascenadev.screenmatch.model;

import br.com.mascenadev.screenmatch.model.enums.Category;

import java.util.OptionalDouble;

public class Serie {

    private String title;
    private String year;
    private Integer totalSeasons;
    private Category genre;
    private Double imdbRating;
    private String plot;
    private String poster;
    private String actors;

    public Serie(DataSeries dataSeries) {
        this.title = dataSeries.title();
        this.year = dataSeries.year();
        this.totalSeasons = dataSeries.totalSeasons();
        this.genre = Category.fromString(dataSeries.genre().split(",")[0].trim());
        this.imdbRating = OptionalDouble.of(Double.valueOf(dataSeries.imdbRating())).orElse(0.0);
        this.plot = dataSeries.plot();
        this.poster = dataSeries.poster();
        this.actors = dataSeries.actors();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return
                "genre=" + genre
                        + ", title='" + title + '\''
                        + ", year=" + year + '\''
                        + ", totalSeasons=" + totalSeasons
                        + ", imdbRating=" + imdbRating
                        + ", plot='" + plot + '\''
                        + ", poster='" + poster + '\''
                        + ", actors='" + actors;
    }
}
