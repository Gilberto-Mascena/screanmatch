package br.com.mascenadev.screenmatch.model;

import br.com.mascenadev.screenmatch.model.enums.Category;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class Serie {

    private String title;
    private Integer year;
    private Integer totalSeasons;
    private Category genre;
    private Double imdbRating;
    private String plot;
    private String poster;
    private String actors;

    public Serie(DataSeries dataSeries) {
        this.title = dataSeries.title();
        this.year = OptionalInt.of(Integer.valueOf(dataSeries.year())).orElse(0);
        this.totalSeasons = dataSeries.totalSeasons();
        this.genre = Category.fromString(dataSeries.genre().split(",")[0].trim());
        this.imdbRating = OptionalDouble.of(Double.valueOf(dataSeries.imdbRating())).orElse(0.0);
        this.plot = dataSeries.plot();
        this.poster = dataSeries.poster();
        this.actors = dataSeries.actors();
    }
}
