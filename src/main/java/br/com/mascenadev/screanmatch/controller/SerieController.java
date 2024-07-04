package br.com.mascenadev.screanmatch.controller;

import br.com.mascenadev.screanmatch.dto.EpisodeDTO;
import br.com.mascenadev.screanmatch.dto.SerieDTO;
import br.com.mascenadev.screanmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> getSeries() {
        return serieService.getAllSeries();
    }

    @GetMapping("/top5")

    public List<SerieDTO> getTop5Series() {
        return serieService.getTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getLaunches() {
        return serieService.getLaunches();
    }

    @GetMapping("/{id}")
    public SerieDTO getSerieById(@PathVariable Long id) {
        return serieService.getSerieById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id) {
        return serieService.getAllSeasons(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Integer numero) {
        return serieService.getSeasonByNumber(id, numero);
    }
}