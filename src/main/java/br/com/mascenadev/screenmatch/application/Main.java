package br.com.mascenadev.screenmatch.application;

import br.com.mascenadev.screenmatch.model.DataSeasons;
import br.com.mascenadev.screenmatch.model.DataSeries;
import br.com.mascenadev.screenmatch.model.Serie;
import br.com.mascenadev.screenmatch.repository.SerieRepository;
import br.com.mascenadev.screenmatch.service.ConsumeApi;
import br.com.mascenadev.screenmatch.service.ConvertData;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {


    private ConsumeApi consumeApi = new ConsumeApi();
    private ConvertData convert = new ConvertData();
    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private Dotenv dotenv = Dotenv.load();
    private final String API_KEY = "&apiKey=" + dotenv.get("OMDB_KEY");

    private Scanner sc = new Scanner(System.in);
    private SerieRepository serieRepository;

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
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
        sc.close();

    }

    private void listSeries() {
        List<Serie>
                series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);

    }

    private void searchEpisodeBySeries() {
        DataSeries dataSeries = getDataSeries();
        List<DataSeasons> seasons = new ArrayList<>();

        for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
            var json = ConsumeApi.getData(ADDRESS + dataSeries.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeasons dataSeason = convert.convertData(json, DataSeasons.class);
            seasons.add(dataSeason);
        }
        seasons.forEach(System.out::println);
    }

    private void searchWebSeries() {
        DataSeries data = getDataSeries();
        //dataSeries.add(data);
        Serie serie = new Serie(data);
        serieRepository.save(serie);
        System.out.println(data);
    }

    private DataSeries getDataSeries() {
        System.out.println("Digite o nome da Série desejada: ");
        var data = sc.nextLine();
        var json = consumeApi.getData(ADDRESS + data.replace(" ", "+") + API_KEY);
        DataSeries dataSeries = convert.convertData(json, DataSeries.class);
        return dataSeries;
    }
}
