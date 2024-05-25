package br.com.mascenadev.screenmatch;

import br.com.mascenadev.screenmatch.application.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(ScreenmatchApplication.class, args);

		}

	@Override
	public void run(String... args) throws Exception {

		Main main = new Main();
		System.out.println();
		main.displaysMenu();

		/* List<DataSeasons> seasons = new ArrayList<>();

		for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
			json = ConsumeApi.getData("http://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=" + dotenv.get("OMDB_KEY"));
			var dataSeason = convert.convertData(json, DataSeasons.class);
			seasons.add(dataSeason);
		}

		seasons.forEach(System.out::println); */

	}
}
