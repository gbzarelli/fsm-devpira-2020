package br.com.helpdev.pokecatcher.domain;

import lombok.EqualsAndHashCode;
import org.springframework.messaging.support.GenericMessage;

@EqualsAndHashCode(callSuper = true)
public class PokeEventMessage extends GenericMessage<PokeEvent> {

  private final Pokemon pokemon;
  private final String[] commands;

  public PokeEventMessage(final Pokemon pokemon, final PokeEvent pokeEvent, final String[] commands) {
    super(pokeEvent);
    this.pokemon = pokemon;
    this.commands = commands;
  }

  public Integer getPowerAttack() {
    return Integer.valueOf(commands.length > 2 ? commands[2] : "0");
  }

  public Pokemon getPokemon() {
    return pokemon;
  }

}
