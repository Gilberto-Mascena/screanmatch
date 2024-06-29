package br.com.mascenadev.screenmatch.repository;

import br.com.mascenadev.screenmatch.model.Episodes;
import br.com.mascenadev.screenmatch.model.Serie;
import br.com.mascenadev.screenmatch.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    // Derived Query Methods
    Optional<Serie> findByTituloContainingIgnoreCase(String nameOfSeries);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nameOfActor, Double rating);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Category category);

    //List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalSeasons, Double rating);

    /*
    Native Query, consult the database directly, using SQL.
    @Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 5 AND avaliacao >= 7.5 ORDER BY avaliacao DESC", nativeQuery = true)
    List<Serie> seriesBySeasonsEvaluation(Integer totalSeasons, Double rating);*/

    // JPQL method
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalSeasons AND avaliacao >= :rating ORDER BY avaliacao DESC")
    List<Serie> seriesBySeasonsEvaluation(Integer totalSeasons, Double rating);

    @Query("SELECT e FROM Serie s JOIN s    .episodios e WHERE e.titulo ILIKE %:excerptEpsode%")
    List<Episodes> findByEpisodesExcerpt(String excerptEpsode);
}
