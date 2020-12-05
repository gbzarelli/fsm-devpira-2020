package br.com.helpdev.fsmdelivery.services;

import static java.util.Optional.ofNullable;

import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import br.com.helpdev.fsmdelivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeliveryStateChangeInterceptor extends StateMachineInterceptorAdapter<DeliveryState, DeliveryEvent> {

  private final DeliveryRepository deliveryRepository;

  @Override
  public void preStateChange(final State<DeliveryState, DeliveryEvent> state,
                             final Message<DeliveryEvent> message,
                             final Transition<DeliveryState, DeliveryEvent> transition,
                             final StateMachine<DeliveryState, DeliveryEvent> stateMachine) {
    ofNullable(message)
        .map(msg -> (Long) msg.getHeaders().getOrDefault(DeliveryServiceImpl.DELIVERY_ID_HEADER, -1L))
        .map(deliveryRepository::getOne)
        .filter(delivery -> delivery.getState() != state.getId())
        .ifPresent(delivery -> {
          delivery.setState(state.getId());
          deliveryRepository.save(delivery);
          System.out.println("New state persisted -> " + delivery.getState());
        });
  }
}
