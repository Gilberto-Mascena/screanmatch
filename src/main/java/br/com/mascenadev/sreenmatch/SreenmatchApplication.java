package br.com.mascenadev.sreenmatch;

import br.com.mascenadev.sreenmatch.service.ConsumeApi;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class SreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(SreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		System.getProperty("OMDB_KEY");

		ConsumeApi consumeApi = new ConsumeApi();
		var json = consumeApi.getData("https://www.omdbapi.com/?i=tt3896198&apikey=" + dotenv.get("OMDB_KEY"));
		System.out.println(json);
		/* System.out.println("Digite o cep: ");
		String cep = sc.nextLine();

		var json = consumeApi.getData(("https://viacep.com.br/ws/" + cep + "/json/"));
		System.out.println(json); */

		sc.close();
	}
}
