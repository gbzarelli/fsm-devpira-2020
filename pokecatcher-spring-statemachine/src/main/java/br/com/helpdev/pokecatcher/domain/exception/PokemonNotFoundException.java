package br.com.helpdev.pokecatcher.domain.exception;

public class PokemonNotFoundException extends RuntimeException {

  public PokemonNotFoundException(final String message) {
    super(message);
  }

}
