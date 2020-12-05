package br.com.helpdev.fsmdelivery.config.actions;

import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class WaitingAcceptanceAction implements Action<DeliveryState, DeliveryEvent> {

  @Override
  public void execute(StateContext<DeliveryState, DeliveryEvent> context) {
    System.out.println("Sending Notification of collection delayed");
  }

}
