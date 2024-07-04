package br.com.mascenadev.screanmatch.controller;

import br.com.mascenadev.screanmatch.dto.SerieDTO;
import br.com.mascenadev.screanmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping("/series")
    public List<SerieDTO> getSeries() {

        return serieService.getAllSeries();
    }
}