package br.com.mascenadev.screanmatch.repository;

import br.com.mascenadev.screanmatch.model.Episode;
import br.com.mascenadev.screanmatch.model.Serie;
import br.com.mascenadev.screanmatch.model.enums.Category;
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
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalSeasons AND s.avaliacao >= :rating ORDER BY s.avaliacao DESC")
    List<Serie> seriesBySeasonsEvaluation(Integer totalSeasons, Double rating);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:excerptEpsode%")
    List<Episode> findByEpisodesExcerpt(String excerptEpsode);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episode> topEpsodesBySeries(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.anoLancamento) >= :yearLaunch")
    List<Episode> episodePerSeriesYear(Serie serie, int yearLaunch);

    @Query("SELECT s FROM Serie s "
           + "JOIN s.episodios e "
           + "GROUP BY s "
           + "ORDER BY MAX(e.anoLancamento) DESC LIMIT 5")
    List<Serie> latestReleases();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
    List<Episode> episodesBySeason(Long id, Integer numero);
}