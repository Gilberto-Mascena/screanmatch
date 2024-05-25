package br.com.mascenadev.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisodes(@JsonAlias("Title") String title,
                           @JsonAlias("Episode") Integer numberEpisode,
                           @JsonAlias("imdbRating") String rating,
                           @JsonAlias("Released") String released) {
}
