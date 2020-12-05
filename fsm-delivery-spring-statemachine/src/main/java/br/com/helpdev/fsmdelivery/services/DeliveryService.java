package br.com.helpdev.fsmdelivery.services;

import br.com.helpdev.fsmdelivery.domain.Delivery;
import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import org.springframework.statemachine.StateMachine;

public interface DeliveryService {

  Delivery newDelivery(Delivery delivery);

  StateMachine<DeliveryState, DeliveryEvent> sendEvent(final Long paymentId, final DeliveryEvent event);

}
