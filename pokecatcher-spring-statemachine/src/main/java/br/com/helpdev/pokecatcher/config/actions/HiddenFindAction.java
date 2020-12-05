package br.com.helpdev.pokecatcher.config.actions;

import java.security.SecureRandom;

import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeEventMessage;
import br.com.helpdev.pokecatcher.domain.PokeState;
import br.com.helpdev.pokecatcher.domain.exception.PokemonNotFoundException;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class HiddenFindAction implements Action<PokeState, PokeEvent> {

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  @Override
  public void execute(final StateContext<PokeState, PokeEvent> context) {
    final var pokeMessage = (PokeEventMessage) context.getMessage();
    final var pokemon = pokeMessage.getPokemon();
    System.out.printf("Trying to find %s...%n", pokemon.getName());

    final var rand = SECURE_RANDOM.nextInt(pokemon.getLevel());
    if (rand > 2)
      throw new PokemonNotFoundException("Uh oh! Pokemon 404!");

    System.out.printf("Wild %s appeared!%n", pokemon.getName());
  }
}
