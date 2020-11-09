package br.com.helpdev.pokecatcher.domain.action;

import java.security.SecureRandom;

import br.com.helpdev.pokecatcher.domain.PokeEventMessage;
import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeState;
import br.com.helpdev.pokecatcher.domain.exception.PokemonNotFoundException;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class FindHiddenAction implements Action<PokeState, PokeEvent> {

  @Override
  public void execute(final StateContext<PokeState, PokeEvent> context) {
    final var pokeMessage = (PokeEventMessage) context.getMessage();
    final var pokemon = pokeMessage.getPokemon();
    System.out.printf("Trying to find %s...%n", pokemon.getName());

    final var rand = new SecureRandom().nextInt(pokemon.getLevel());
    if (rand > 2)
      throw new PokemonNotFoundException("Uh oh! Pokemon 404!");

    System.out.printf("Wild %s appeared!%n", pokemon.getName());
  }
}
