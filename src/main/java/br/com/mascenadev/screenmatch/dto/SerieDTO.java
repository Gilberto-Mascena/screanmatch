package br.com.mascenadev.screenmatch.dto;

import br.com.mascenadev.screenmatch.model.enums.Category;

public record SerieDTO(Long id,
                       String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Category genero,
                       String atores,
                       String poster,
                       String sinopse) {
}