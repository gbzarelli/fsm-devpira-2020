package br.com.helpdev.pokecatcher.config.actions;

import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeState;
import br.com.helpdev.pokecatcher.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisibleTimeAction implements Action<PokeState, PokeEvent> {

  private final PokemonRepository pokemonRepository;

  @Override
  public void execute(StateContext<PokeState, PokeEvent> context) {
    final var pokemon = pokemonRepository.getPokemonByUuid(
        Integer.valueOf(context.getStateMachine().getId())
    ).orElseThrow();

    System.out.printf("The %s run away...%n", pokemon.getName());
  }
}