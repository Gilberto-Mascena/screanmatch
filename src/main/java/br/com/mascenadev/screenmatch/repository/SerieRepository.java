package br.com.mascenadev.screenmatch.repository;

import br.com.mascenadev.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
