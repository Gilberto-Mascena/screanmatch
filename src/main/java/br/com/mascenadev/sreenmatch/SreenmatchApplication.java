package br.com.mascenadev.sreenmatch;

import br.com.mascenadev.sreenmatch.service.ConsumeApi;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(SreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		System.getProperty("OMDB_KEY");

		ConsumeApi consumeApi = new ConsumeApi();
		var json = consumeApi.getData("https://www.omdbapi.com/?i=tt3896198&apikey=" + dotenv.get("OMDB_KEY"));
		System.out.println(json);
	}
}
