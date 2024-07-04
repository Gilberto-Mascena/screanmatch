package br.com.mascenadev.screanmatch.service;

import br.com.mascenadev.screanmatch.dto.SerieDTO;
import br.com.mascenadev.screanmatch.model.Serie;
import br.com.mascenadev.screanmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getAllSeries() {
        return convertToDTO(serieRepository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return convertToDTO(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> convertToDTO(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse())).collect(Collectors.toList());
    }

    public List<SerieDTO> getLaunches() {
        return convertToDTO(serieRepository.latestReleases());
    }

    public SerieDTO getSerieById(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        Serie s = serie.get();

        if (serie.isPresent()) {
            return new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getGenero(),
                    s.getAtores(),
                    s.getPoster(),
                    s.getSinopse());
        }
        return null;
    }
}