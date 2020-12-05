package br.com.helpdev.fsmdelivery.config.actions;

import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class DeliveryAcceptedAction implements Action<DeliveryState, DeliveryEvent> {

  @Override
  public void execute(StateContext<DeliveryState, DeliveryEvent> context) {
    System.out.println("DeliveryAcceptedAction was called!!!");
  }
}
