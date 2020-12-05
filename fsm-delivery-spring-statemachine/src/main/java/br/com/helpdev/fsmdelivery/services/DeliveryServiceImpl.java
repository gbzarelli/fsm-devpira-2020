package br.com.helpdev.fsmdelivery.services;

import br.com.helpdev.fsmdelivery.domain.Delivery;
import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import br.com.helpdev.fsmdelivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {
  public static final String DELIVERY_ID_HEADER = "payment_id";

  private final DeliveryRepository deliveryRepository;
  private final StateMachineFactory<DeliveryState, DeliveryEvent> stateMachineFactory;
  private final DeliveryStateChangeInterceptor deliveryStateChangeInterceptor;

  @Override
  public Delivery newDelivery(final Delivery delivery) {
    delivery.setState(DeliveryState.WAITING_COLLECTION);
    return deliveryRepository.save(delivery);
  }

  @Transactional
  @Override
  public StateMachine<DeliveryState, DeliveryEvent> sendEvent(final Long paymentId, final DeliveryEvent event) {
    StateMachine<DeliveryState, DeliveryEvent> sm = build(paymentId);
    sendEvent(paymentId, sm, event);
    return sm;
  }

  private void sendEvent(final Long paymentId,
                         final StateMachine<DeliveryState, DeliveryEvent> sm,
                         final DeliveryEvent event) {
    final var msg = MessageBuilder.withPayload(event)
        .setHeader(DELIVERY_ID_HEADER, paymentId)
        .build();
    final var accepted = sm.sendEvent(msg);
    System.out.println("Event accepted? : " + accepted);
  }

  private StateMachine<DeliveryState, DeliveryEvent> build(final Long paymentId) {
    final var payment = deliveryRepository.getOne(paymentId);
    final var machine = stateMachineFactory.getStateMachine(String.valueOf(paymentId));

    machine.stop();
    machine.getStateMachineAccessor()
        .doWithAllRegions(sma -> {
          sma.addStateMachineInterceptor(deliveryStateChangeInterceptor);
          sma.resetStateMachine(new DefaultStateMachineContext<>(payment.getState(), null, null, null));
        });
    machine.start();

    return machine;
  }
}
