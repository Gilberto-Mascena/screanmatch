package br.com.mascenadev.screenmatch.application;

import br.com.mascenadev.screenmatch.model.DataSeasons;
import br.com.mascenadev.screenmatch.model.DataSeries;
import br.com.mascenadev.screenmatch.model.Episodes;
import br.com.mascenadev.screenmatch.service.ConsumeApi;
import br.com.mascenadev.screenmatch.service.ConvertData;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;
import java.util.stream.Collectors;

public class Main {


    private ConsumeApi consumeApi = new ConsumeApi();
    private ConvertData convert = new ConvertData();
    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private Dotenv dotenv = Dotenv.load();
    private final String API_KEY = "&apiKey=" + dotenv.get("OMDB_KEY");

    private Scanner sc = new Scanner(System.in);

    public void displaysMenu() {

         System.out.println("Digite o nome da Série desejada: ");
        var data = sc.nextLine();
        var json = consumeApi.getData(ADDRESS + data.replace(" ", "+") + API_KEY);
        DataSeries dataSeries = convert.convertData(json, DataSeries.class);
        System.out.println(dataSeries);

        List<DataSeasons> seasons = new ArrayList<>();

		for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
			json = ConsumeApi.getData(ADDRESS + data.replace(" ", "+") + "&season=" + i + "&apikey=" + dotenv.get("OMDB_KEY"));
			DataSeasons dataSeason = convert.convertData(json, DataSeasons.class);
			seasons.add(dataSeason);
		}

		/*seasons.forEach(System.out::println);

         List<DataEpisodes> dataEpisodes = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episódios com maior avaliação: ");
        dataEpisodes.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DataEpisodes::rating).reversed())
                .limit(5)
                .forEach(System.out::println);*/

        List<Episodes> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                .map(d -> new Episodes(t.season(), d)))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);

        /*System.out.println("Digte o nome do episódio que deseja ver: ");

        var episode = sc.nextLine();

        Optional<Episodes> episodeSearch = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(episode.toUpperCase()))
                .findFirst();

        if (episodeSearch.isPresent()) {
            System.out.println("Episódio encontrado! \nTemporada :" + episodeSearch.get().getSeason());
        } else {
            System.out.println("Episódio não encontrado");
        }*/

        /*System.out.println("A partir de qual ano deseja ver os episódios? ");
        var year = sc.nextInt();
        System.out.println();

        LocalDate date = LocalDate.of(year, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e -> e.getReleased() != null && e.getReleased().isAfter(date))
                .forEach(e -> System.out.println(
                        "Temporada: " +e.getSeason()
                        + " Episódio: " + e.getNumberEpisode()
                        + " Data de Lançamento: " + e.getReleased().format(formatter)
                ));*/

        Map<Integer, Double> reviewSeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episodes::getSeason,
                Collectors.averagingDouble(Episodes::getRating)));

        System.out.println("\nMédia de avaliação por temporada: " + reviewSeason);


        sc.close();
    }
}
