package br.com.helpdev.pokecatcher.config.guards;

import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeEventMessage;
import br.com.helpdev.pokecatcher.domain.PokeState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class AttackGuard implements Guard<PokeState, PokeEvent> {

  @Override
  public boolean evaluate(final StateContext<PokeState, PokeEvent> context) {
    final var pokeEventMessage = (PokeEventMessage) context.getMessage();
    if (pokeEventMessage.getPowerAttack() > 0) return true;
    System.out.println("You need pass the power attack.");
    return false;
  }

}
