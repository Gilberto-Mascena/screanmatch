package br.com.mascenadev.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(@JsonAlias("Title") String title,
                         @JsonAlias("Year") String year,
                         @JsonAlias("totalSeasons") Integer totalSeasons,
                         @JsonAlias("Genre") String genre,
                         @JsonAlias("imdbRating") String imdbRating,
                         @JsonAlias("Plot") String plot,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Actors") String actors) {
}
