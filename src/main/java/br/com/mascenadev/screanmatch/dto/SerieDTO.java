package br.com.mascenadev.screanmatch.dto;

import br.com.mascenadev.screanmatch.model.enums.Category;

public record SerieDTO(Long id,
                       String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Category genero,
                       String atores,
                       String poster,
                       String sinopse) {
}