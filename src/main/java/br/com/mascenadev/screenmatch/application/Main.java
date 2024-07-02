package br.com.mascenadev.screenmatch.application;

import br.com.mascenadev.screenmatch.model.DataSeasons;
import br.com.mascenadev.screenmatch.model.DataSeries;
import br.com.mascenadev.screenmatch.model.Episodes;
import br.com.mascenadev.screenmatch.model.Serie;
import br.com.mascenadev.screenmatch.model.enums.Category;
import br.com.mascenadev.screenmatch.repository.SerieRepository;
import br.com.mascenadev.screenmatch.service.ConsumeApi;
import br.com.mascenadev.screenmatch.service.ConvertData;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.util.stream.Collectors;

@Profile("dev")
public class Main {

    private ConsumeApi consumeApi = new ConsumeApi();
    private ConvertData convert = new ConvertData();
    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = "&apiKey=" + dotenv.get("OMDB_KEY");
    private final Scanner sc = new Scanner(System.in);
    private final SerieRepository serieRepository;
    private List<Serie> series = new ArrayList<>();
    private Optional<Serie> seriesSearch;

    public Main(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void displaysMenu() {

        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar série
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por titulo
                    5 - Buscar séries por ator
                    6 - Top 5 séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries
                    9 - Buscar episódio por trecho
                    10 - Top 5 episódios por série
                                 
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    searchWebSeries();
                    break;
                case 2:
                    searchEpisodeBySeries();
                    break;
                case 3:
                    listSeries();
                    break;
                case 4:
                    searchSeriesByTitle();
                    break;
                case 5:
                    searchSeriesByActor();
                    break;
                case 6:
                    searchTop5Series();
                    break;
                case 7:
                    searchSeriesByCategory();
                    break;
                case 8:
                    filterSeries();
                    break;
                case 9:
                    searchEpsodeByExcerpt();
                    break;
                case 10:
                    topEpsodesBySeries();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida\nSelecione uma das opções abaixo!\n");
            }
        }
        sc.close();

    }

    private DataSeries getDataSeries() {

        System.out.println("Digite o nome da Série desejada: ");
        var data = sc.nextLine();
        var json = consumeApi.getData(ADDRESS + data.replace(" ", "+") + API_KEY);
        DataSeries dataSeries = convert.convertData(json, DataSeries.class);
        return dataSeries;
    }

    private void searchWebSeries() {

        DataSeries data = getDataSeries();
        Serie serie = new Serie(data);
        serieRepository.save(serie);
        System.out.println(data);
    }

    private void searchEpisodeBySeries() {

        listSeries();
        System.out.println("Digite o nome da Série desejada: ");
        var nameSerie = sc.nextLine();

        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nameSerie);

        if (serie.isPresent()) {

            var foundSeries = serie.get();
            List<DataSeasons> seasons = new ArrayList<>();

            for (int i = 1; i <= foundSeries.getTotalTemporadas(); i++) {

                var json = ConsumeApi.getData(ADDRESS + foundSeries.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeasons dataSeason = convert.convertData(json, DataSeasons.class);
                seasons.add(dataSeason);
            }
            seasons.forEach(System.out::println);

            List<Episodes> episodes = seasons.stream()
                    .flatMap((d -> d.episodios().stream()
                            .map(e -> new Episodes(d.temporada(), e))))
                    .collect(Collectors.toList());
            foundSeries.setEpisodes(episodes);
            serieRepository.save(foundSeries);
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void listSeries() {

        series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }

    private void searchSeriesByTitle() {

        System.out.println("Digite o nome da Série desejada: ");
        var nameSerie = sc.nextLine();
        seriesSearch = serieRepository.findByTituloContainingIgnoreCase(nameSerie);
        if (seriesSearch.isPresent()) {
            System.out.println("Dados da série: " + seriesSearch.get());
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void searchSeriesByActor() {

        System.out.println("Digite o nome do ator / atriz: ");
        var nameOfActor = sc.nextLine();
        System.out.println("Avaliação a partir de qual nota?");
        var rating = sc.nextDouble();
        List<Serie> seriesFound = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nameOfActor, rating);
        System.out.println("Séries em que o ator / atriz " + nameOfActor + " participou: ");
        seriesFound.forEach(s ->
                System.out.printf("Série: '%s', Avaliação: %s\n",
                        s.getTitulo(),
                        s.getAvaliacao()));
    }

    private void searchTop5Series() {

        List<Serie> seriesTopFive = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        seriesTopFive.forEach(s ->
                System.out.printf("Série: '%s' - Avaliação: %s\n",
                        s.getTitulo(),
                        s.getAvaliacao()));
    }

    private void searchSeriesByCategory() {

        System.out.println("Digite a categoria desejada / gênero: ");
        var category = sc.nextLine();
        Category categotyGenre = Category.fromStringPortuguese(category);
        List<Serie> seriesFound = serieRepository.findByGenero(categotyGenre);
        System.out.println("\n*** Séries da categoria " + category + " ***");
        seriesFound.forEach(System.out::println);
    }

    private void filterSeries() {

        System.out.println("Digite o número de temporadas desejado: ");
        var totalSeasons = sc.nextInt();
        System.out.println("Avaliação a partir de qual nota? ");
        var rating = sc.nextDouble();
        List<Serie> seriesFound = serieRepository.seriesBySeasonsEvaluation(totalSeasons, rating);
        seriesFound.forEach(s ->
                System.out.printf("Série encontrada: '%s', Temporadas - %s, Avaliação: %s\n",
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao()));
    }

    private void searchEpsodeByExcerpt() {

        System.out.println("Digite o trecho do episódio desejado: ");
        var excerptEpsode = sc.nextLine();
        List<Episodes> epsodeFound = serieRepository.findByEpisodesExcerpt(excerptEpsode);
        epsodeFound.forEach(e ->
                System.out.printf("Séries: '%s', Temporada - %s, Epsódios %s, Titulo do epsódio - '%s', Avaliação: %s\n",
                        e.getSerie().getTitulo(),
                        e.getTemporada(),
                        e.getEpisodio(),
                        e.getTitulo(),
                        e.getAvaliacao()));
    }

    private void topEpsodesBySeries() {

        searchSeriesByTitle();
        if (seriesSearch.isPresent()) {
            Serie serie = seriesSearch.get();
            List<Episodes> topEpsodes = serieRepository.topEpsodesBySeries(serie);
            topEpsodes.forEach(e ->
                    System.out.printf("Séries: '%s', Temporadas - %s, Epsódios - %s, Titulo do epsódio - '%s', - Avaliação: %s\n",
                            e.getSerie().getTitulo(),
                            e.getTemporada(),
                            e.getEpisodio(),
                            e.getTitulo(),
                            e.getAvaliacao()));
        }
    }
}