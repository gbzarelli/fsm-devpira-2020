package br.com.helpdev.pokecatcher.config;

import java.util.EnumSet;

import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeState;
import br.com.helpdev.pokecatcher.domain.action.FindHiddenAction;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
public class SimpleStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<PokeState, PokeEvent> {

  @Override
  public void configure(final StateMachineStateConfigurer<PokeState, PokeEvent> states) throws Exception {
    states.withStates()
        .initial(PokeState.HIDDEN)
        .states(EnumSet.allOf(PokeState.class))
        .end(PokeState.CAPTURED).end(PokeState.DEAD);
  }

  @Override
  public void configure(final StateMachineTransitionConfigurer<PokeState, PokeEvent> transitions) throws Exception {
    transitions.withExternal()
        .source(PokeState.HIDDEN).event(PokeEvent.FIND).target(PokeState.VISIBLE).action(new FindHiddenAction())
        .and().withExternal()
        .source(PokeState.VISIBLE).event(PokeEvent.ATTACK).target(PokeState.INJURED)
        .and().withExternal()
        .source(PokeState.VISIBLE).event(PokeEvent.CATCH).target(PokeState.HIDDEN)
        .and().withExternal()
        .source(PokeState.VISIBLE).event(PokeEvent.OBSERVE).target(PokeState.VISIBLE)
        .and().withExternal()
        .source(PokeState.INJURED).event(PokeEvent.ATTACK).target(PokeState.DEAD)
        .and().withExternal()
        .source(PokeState.INJURED).event(PokeEvent.CATCH).target(PokeState.CAPTURED)
        .and().withExternal()
        .source(PokeState.INJURED).event(PokeEvent.OBSERVE).target(PokeState.HIDDEN);
  }

}
