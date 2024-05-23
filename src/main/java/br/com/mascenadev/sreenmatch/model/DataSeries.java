package br.com.mascenadev.sreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataSeries (@JsonAlias("Title") String title,
                          @JsonAlias("Year") String year,
                          @JsonAlias("Genre") String genre) {
}
