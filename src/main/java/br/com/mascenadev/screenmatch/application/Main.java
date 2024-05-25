package br.com.mascenadev.screenmatch.application;

import br.com.mascenadev.screenmatch.model.DataSeries;
import br.com.mascenadev.screenmatch.service.ConsumeApi;
import br.com.mascenadev.screenmatch.service.ConvertData;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

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

        sc.close();
    }
}
