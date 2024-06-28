package br.com.mascenadev.screenmatch.repository;

import br.com.mascenadev.screenmatch.model.Serie;
import br.com.mascenadev.screenmatch.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie>findByTituloContainingIgnoreCase(String nameOfSeries);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nameOfActor, Double rating);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Category category);
}
