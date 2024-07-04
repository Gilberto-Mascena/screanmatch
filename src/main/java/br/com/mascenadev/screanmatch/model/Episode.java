package br.com.mascenadev.screanmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate anoLancamento;

    @ManyToOne
    private Serie serie;

    public Episode() {
    }

    public Episode(Integer temporada, DataEpisodes dataEpisodes) {
        this.temporada = temporada;
        this.titulo = dataEpisodes.titulo();
        this.numeroEpisodio = dataEpisodes.numero();

        try {
            this.avaliacao = Double.parseDouble(dataEpisodes.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }

        try {
            this.anoLancamento = LocalDate.parse(dataEpisodes.anoLancamento());
        } catch (DateTimeParseException ex) {
            this.anoLancamento = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(LocalDate anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "temporada= " + temporada +
               ", titulo= '" + titulo + '\'' +
               ", epsodio= " + numeroEpisodio +
               ", avaliação= " + avaliacao +
               ", anoLançamento= " + anoLancamento;
    }
}