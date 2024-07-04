package br.com.mascenadev.screanmatch.application;

import br.com.mascenadev.screanmatch.model.DataSeasons;
import br.com.mascenadev.screanmatch.model.DataSeries;
import br.com.mascenadev.screanmatch.model.Episodes;
import br.com.mascenadev.screanmatch.model.Serie;
import br.com.mascenadev.screanmatch.model.enums.Category;
import br.com.mascenadev.screanmatch.repository.SerieRepository;
import br.com.mascenadev.screanmatch.service.ConsumeApi;
import br.com.mascenadev.screanmatch.service.ConvertData;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.util.stream.Collectors;

@Profile("dev")
public class Main {

    private final Scanner sc = new Scanner(System.in);
    private ConsumeApi consumeApi = new ConsumeApi();
    private ConvertData convert = new ConvertData();
    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = "&apiKey=" + dotenv.get("OMDB_KEY");

    private SerieRepository serieRepository;
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
                    11 - Buscar episódio apartir de uma data
                                 
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
                    listSeriesSearch();
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
                    filterSeriesByCategoryEvaluation();
                    break;
                case 9:
                    searchEpsodeByExcerpt();
                    break;
                case 10:
                    topEpsodesBySeries();
                    break;
                case 11:
                    searchEpsodeByDate();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida\nSelecione uma das opções abaixo!\n");
            }
        }
    }

    private void searchWebSeries() {

        DataSeries data = getDataSeries();
        Serie serie = new Serie(data);
        serieRepository.save(serie);
        System.out.println(data);
    }

    private DataSeries getDataSeries() {

        System.out.println("Digite o nome da série para busca: ");
        var nameOfSeries = sc.nextLine();
        var json = consumeApi.getData(ADDRESS + nameOfSeries.replace(" ", "+") + API_KEY);
        DataSeries dataSeries = convert.getData(json, DataSeries.class);
        return dataSeries;
    }

    private void searchEpisodeBySeries() {

        listSeriesSearch();
        System.out.println("Digite o nome da série desejada: ");
        var nameSerie = sc.nextLine();

        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nameSerie);

        if (serie.isPresent()) {

            var foundSeries = serie.get();
            List<DataSeasons> seasons = new ArrayList<>();

            for (int i = 1; i <= foundSeries.getTotalTemporadas(); i++) {

                var json = ConsumeApi.getData(ADDRESS + foundSeries.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeasons dataSeason = convert.getData(json, DataSeasons.class);
                seasons.add(dataSeason);
            }
            seasons.forEach(System.out::println);

            List<Episodes> episodes = seasons.stream()
                    .flatMap((d -> d.episodios().stream()
                            .map(e -> new Episodes(d.numero(), e))))
                    .collect(Collectors.toList());
            foundSeries.setEpisodes(episodes);
            serieRepository.save(foundSeries);
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void listSeriesSearch() {

        series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }

    private void searchSeriesByTitle() {

        System.out.println("Digite o nome da série desejada: ");
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
        List<Serie> seriesByCategory = serieRepository.findByGenero(categotyGenre);
        System.out.println("\n*** Séries da categoria " + category + " ***");
        seriesByCategory.forEach(System.out::println);
    }

    private void filterSeriesByCategoryEvaluation() {

        System.out.println("Digite o número de temporadas desejado: ");
        var totalSeasons = sc.nextInt();
        sc.nextLine();
        System.out.println("Avaliação a partir de qual nota? ");
        var rating = sc.nextDouble();
        sc.nextLine();
        List<Serie> filterSeries = serieRepository.seriesBySeasonsEvaluation(totalSeasons, rating);
        filterSeries.forEach(s ->
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
                        e.getNumeroEpisodio(),
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
                            e.getNumeroEpisodio(),
                            e.getTitulo(),
                            e.getAvaliacao()));
        }
    }

    private void searchEpsodeByDate() {

        searchSeriesByTitle();
        if (seriesSearch.isPresent()) {
            Serie serie = seriesSearch.get();
            System.out.println("Digite a data desejada  do lamçamento: ");
            var yearLaunch = sc.nextInt();
            sc.nextLine();
            List<Episodes> episodeYear = serieRepository.episodePerSeriesYear(serie, yearLaunch);
            episodeYear.forEach(System.out::println);
        }
    }
}