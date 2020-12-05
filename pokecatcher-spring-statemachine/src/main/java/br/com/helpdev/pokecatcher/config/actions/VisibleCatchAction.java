package br.com.helpdev.pokecatcher.config.actions;

import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class VisibleCatchAction implements Action<PokeState, PokeEvent> {
  @Override
  public void execute(StateContext<PokeState, PokeEvent> context) {

  }
}