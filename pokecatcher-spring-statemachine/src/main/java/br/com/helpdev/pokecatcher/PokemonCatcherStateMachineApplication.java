package br.com.helpdev.pokecatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PokemonCatcherStateMachineApplication {

  public static void main(String[] args) {
    SpringApplication.run(PokemonCatcherStateMachineApplication.class, args);
  }

  @Bean
  BufferedReader bufferedReader() {
    return new BufferedReader(new InputStreamReader(System.in));
  }

}
