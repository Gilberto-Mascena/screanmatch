package br.com.mascenadev.screenmatch;

import br.com.mascenadev.screenmatch.application.Main;
import br.com.mascenadev.screenmatch.repository.SerieRepository;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    @Resource
    private SerieRepository serieRepository;

    public static void main(String[] args) {

        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Main main = new Main(serieRepository);

        System.out.println();
        main.displaysMenu();
    }
}