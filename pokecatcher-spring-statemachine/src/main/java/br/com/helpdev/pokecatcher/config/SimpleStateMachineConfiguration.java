package br.com.helpdev.pokecatcher.config;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.EnumSet;

import br.com.helpdev.pokecatcher.config.actions.AttackAction;
import br.com.helpdev.pokecatcher.config.actions.HiddenFindAction;
import br.com.helpdev.pokecatcher.config.actions.InjuredCatchAction;
import br.com.helpdev.pokecatcher.config.actions.InjuredObserveAction;
import br.com.helpdev.pokecatcher.config.actions.VisibleCatchAction;
import br.com.helpdev.pokecatcher.config.actions.VisibleObserveAction;
import br.com.helpdev.pokecatcher.config.actions.VisibleTimeAction;
import br.com.helpdev.pokecatcher.config.guards.AttackGuard;
import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class SimpleStateMachineConfiguration extends EnumStateMachineConfigurerAdapter<PokeState, PokeEvent> {

  private static final long VISIBLE_TO_HIDDEN_PERIOD = SECONDS.toMillis(10);

  private final HiddenFindAction hiddenFindAction;
  private final VisibleCatchAction visibleCatchAction;
  private final VisibleObserveAction visibleObserveAction;
  private final VisibleTimeAction visibleTimeAction;
  private final AttackAction attackAction;
  private final InjuredCatchAction injuredCatchAction;
  private final InjuredObserveAction injuredObserveAction;
  private final AttackGuard attackGuard;

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
        .source(PokeState.HIDDEN).event(PokeEvent.FIND).target(PokeState.VISIBLE).action(hiddenFindAction)
        .and().withExternal()
        .source(PokeState.VISIBLE).event(PokeEvent.ATTACK).target(PokeState.INJURED).guard(attackGuard).action(attackAction)
        .and().withExternal()
        .source(PokeState.VISIBLE).event(PokeEvent.CATCH).target(PokeState.HIDDEN).action(visibleCatchAction)
        .and().withExternal()
        .source(PokeState.VISIBLE).event(PokeEvent.OBSERVE).target(PokeState.VISIBLE).action(visibleObserveAction)
        .and().withExternal()
        .source(PokeState.VISIBLE).timerOnce(VISIBLE_TO_HIDDEN_PERIOD).target(PokeState.HIDDEN).action(visibleTimeAction)
        .and().withExternal()
        .source(PokeState.INJURED).event(PokeEvent.ATTACK).target(PokeState.DEAD).guard(attackGuard).action(attackAction)
        .and().withExternal()
        .source(PokeState.INJURED).event(PokeEvent.CATCH).target(PokeState.CAPTURED).action(injuredCatchAction)
        .and().withExternal()
        .source(PokeState.INJURED).event(PokeEvent.OBSERVE).target(PokeState.HIDDEN).action(injuredObserveAction);
  }

  @Override
  public void configure(final StateMachineConfigurationConfigurer<PokeState, PokeEvent> config) throws Exception {
    final var listener = new StateMachineListenerAdapter<PokeState, PokeEvent>() {
      @Override
      public void stateChanged(State<PokeState, PokeEvent> from, State<PokeState, PokeEvent> to) {
        System.out.printf("\tStateChanged(from: %s, to: %s)%n", from == null ? "null" : from.getId(), to == null ? "null" : to.getId());
      }
    };
    config.withConfiguration().listener(listener);
  }
}
