package br.com.helpdev.pokecatcher.repository;

import java.util.Optional;

import br.com.helpdev.pokecatcher.domain.Pokemon;

public interface PokemonRepository {

  Optional<Pokemon> getPokemonByUuid(final Integer uuid);

}
