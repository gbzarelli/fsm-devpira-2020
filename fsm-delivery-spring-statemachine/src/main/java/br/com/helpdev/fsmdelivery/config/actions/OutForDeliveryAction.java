package br.com.helpdev.fsmdelivery.config.actions;

import static java.util.Optional.ofNullable;

import java.time.ZonedDateTime;
import java.util.Optional;

import br.com.helpdev.fsmdelivery.domain.Delivery;
import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.domain.DeliveryState;
import br.com.helpdev.fsmdelivery.repository.DeliveryRepository;
import br.com.helpdev.fsmdelivery.services.DeliveryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OutForDeliveryAction implements Action<DeliveryState, DeliveryEvent> {

  private final DeliveryRepository deliveryRepository;

  @Override
  public void execute(StateContext<DeliveryState, DeliveryEvent> context) {
    System.out.println("OutForDeliveryAction was called!!!");

    getDeliveryIdFromHeader(context)
        .map(deliveryRepository::getOne)
        .ifPresent(this::updateOutForDeliveryDateTime);
  }

  private Optional<Long> getDeliveryIdFromHeader(final StateContext<DeliveryState, DeliveryEvent> context) {
    return ofNullable(context.getMessage())
        .map(msg -> (Long) msg.getHeaders().getOrDefault(DeliveryServiceImpl.DELIVERY_ID_HEADER, -1L));
  }

  private void updateOutForDeliveryDateTime(final Delivery delivery) {
    delivery.setOutForDeliveryDateTime(ZonedDateTime.now());
    deliveryRepository.save(delivery);
  }
}
