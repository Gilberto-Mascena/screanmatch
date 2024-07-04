package br.com.mascenadev.screanmatch.controller;

import br.com.mascenadev.screanmatch.dto.SerieDTO;
import br.com.mascenadev.screanmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<SerieDTO> getSeries() {
        return serieRepository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse())).collect(Collectors.toList());
    }

    @GetMapping("/home")
    public String getHome() {
        return "Hello World!";
    }
}