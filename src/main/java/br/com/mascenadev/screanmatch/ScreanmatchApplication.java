package br.com.mascenadev.screanmatch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("test")
public class ScreanmatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreanmatchApplication.class, args);
    }
}