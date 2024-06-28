package br.com.mascenadev.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisodes(@JsonAlias("Title") String titulo,
                           @JsonAlias("Episode") Integer episodio,
                           @JsonAlias("imdbRating") String avaliacao,
                           @JsonAlias("Released") String anoLancamento) {
}
