package br.com.helpdev.fsmdelivery.config.guards;

import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import br.com.helpdev.fsmdelivery.services.DeliveryServiceImpl;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class DeliveryIdGuard implements Guard<DeliveryState, DeliveryEvent> {

  @Override
  public boolean evaluate(StateContext<DeliveryState, DeliveryEvent> context) {
    return context.getMessageHeader(DeliveryServiceImpl.DELIVERY_ID_HEADER) != null;
  }
}
