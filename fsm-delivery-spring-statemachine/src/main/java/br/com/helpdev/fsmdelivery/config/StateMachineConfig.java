package br.com.helpdev.fsmdelivery.config;

import java.util.EnumSet;

import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
@RequiredArgsConstructor
@EnableStateMachineFactory
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<DeliveryState, DeliveryEvent> {

  private final Action<DeliveryState, DeliveryEvent> outForDeliveryAction;
  private final Action<DeliveryState, DeliveryEvent> deliveryRejectedAction;
  private final Guard<DeliveryState, DeliveryEvent> deliveryIdGuard;
  private final Action<DeliveryState, DeliveryEvent> deliveryCanceledAction;
  private final Action<DeliveryState, DeliveryEvent> waitingAcceptanceAction;

  @Override
  public void configure(StateMachineStateConfigurer<DeliveryState, DeliveryEvent> states) throws Exception {
    states.withStates()
        .initial(DeliveryState.WAITING_COLLECTION)
        .states(EnumSet.allOf(DeliveryState.class))
        .end(DeliveryState.RETURNED)
        .end(DeliveryState.DELIVERED)
        .end(DeliveryState.CANCELED);
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<DeliveryState, DeliveryEvent> transitions) throws Exception {
    transitions.withExternal()
        .and()
        .withExternal().source(DeliveryState.WAITING_COLLECTION).target(DeliveryState.OUT_FOR_DELIVERY).event(DeliveryEvent.PICKED_UP)
        .action(outForDeliveryAction).guard(deliveryIdGuard)
        .and()
        .withExternal().source(DeliveryState.WAITING_COLLECTION).target(DeliveryState.CANCELED).event(DeliveryEvent.USER_GIVE_UP)
        .action(deliveryCanceledAction)
        //--
        .and()
        .withExternal().source(DeliveryState.OUT_FOR_DELIVERY).target(DeliveryState.WAITING_ACCEPTANCE).event(DeliveryEvent.ARRIVED)
        .action(waitingAcceptanceAction)
        //--
        .and()
        .withExternal().source(DeliveryState.WAITING_ACCEPTANCE).target(DeliveryState.DELIVERED).event(DeliveryEvent.ACCEPT)
        .and()
        .withExternal().source(DeliveryState.WAITING_ACCEPTANCE).target(DeliveryState.RETURNED).event(DeliveryEvent.REJECT)
        .action(deliveryRejectedAction);
  }


  @Override
  public void configure(StateMachineConfigurationConfigurer<DeliveryState, DeliveryEvent> config) throws Exception {
    StateMachineListenerAdapter<DeliveryState, DeliveryEvent> adapter = new StateMachineListenerAdapter<>() {
      @Override
      public void stateChanged(State<DeliveryState, DeliveryEvent> from, State<DeliveryState, DeliveryEvent> to) {
        log.trace(String.format("stateChanged(from: %s, to: %s)", from, to));
      }
    };

    config.withConfiguration().listener(adapter);
  }


}
