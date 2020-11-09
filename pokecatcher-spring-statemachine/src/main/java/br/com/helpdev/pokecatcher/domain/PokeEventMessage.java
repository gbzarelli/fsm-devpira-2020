package br.com.helpdev.pokecatcher.domain;

import java.util.Objects;

import org.springframework.messaging.support.GenericMessage;

public class PokeEventMessage extends GenericMessage<PokeEvent> {

  private final Pokemon pokemon;

  public PokeEventMessage(final Pokemon pokemon, final PokeEvent pokeEvent) {
    super(pokeEvent);
    this.pokemon = pokemon;
  }

  public Pokemon getPokemon() {
    return pokemon;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    return Objects.equals(pokemon, ((PokeEventMessage) o).pokemon);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (pokemon != null ? pokemon.hashCode() : 0);
    return result;
  }
}
