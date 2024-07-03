package br.com.mascenadev.screenmatch.model;

import br.com.mascenadev.screenmatch.model.enums.Category;
import br.com.mascenadev.screenmatch.service.ChatGPTConsultation;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    private Category genero;
    private String atores;
    private String poster;
    private String sinopse;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodes> episodios = new ArrayList<>();

    public Serie() {
    }

    public Serie(DataSeries dataSeries) {
        this.titulo = dataSeries.titulo();
        this.totalTemporadas = dataSeries.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.parseDouble(dataSeries.avaliacao())).orElse(0.0);
        this.genero = Category.fromString(dataSeries.genero().split(",")[0].trim());
        this.atores = dataSeries.atores();
        this.poster = dataSeries.poster();
        this.sinopse = ChatGPTConsultation.getTranslation(dataSeries.sinopse()).trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Episodes> getEpisodes() {
        return episodios;
    }

    public void setEpisodes(List<Episodes> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Category getGenero() {
        return genero;
    }

    public void setGenero(Category genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return
                "genero= " + genero
                + ", titulo= '" + titulo + '\''
                + ", totalTemporadas= " + totalTemporadas
                + ", avaliacao= " + avaliacao
                + ", sinopse= '" + sinopse + '\''
                + ", poster= '" + poster + '\''
                + ", atores= '" + atores + '\''
                + ", episodes= " + episodios;
    }
}