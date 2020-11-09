package br.com.helpdev.pokecatcher.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.helpdev.pokecatcher.domain.Pokemon;
import org.springframework.stereotype.Component;

@Component
public class PokemonRepository {

  private static final Map<Integer, Pokemon> POKEMONS_DATABASE_SAMPLE;

  static {
    POKEMONS_DATABASE_SAMPLE = new HashMap<>();
    final var poke1 = new Pokemon(1, "Bulbasaur", 3);
    final var poke2 = new Pokemon(2, "Charizard", 5);
    final var poke3 = new Pokemon(3, "Magikarp", 1);
    POKEMONS_DATABASE_SAMPLE.put(poke1.getId(), poke1);
    POKEMONS_DATABASE_SAMPLE.put(poke2.getId(), poke2);
    POKEMONS_DATABASE_SAMPLE.put(poke3.getId(), poke3);
    System.out.println("Pokemons:\n\t" + POKEMONS_DATABASE_SAMPLE);
  }

  public Optional<Pokemon> getPokemonByUuid(final Integer uuid) {
    return Optional.ofNullable(POKEMONS_DATABASE_SAMPLE.get(uuid));
  }

}
