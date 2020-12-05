package br.com.helpdev.fsmdelivery.entrypoint;

import br.com.helpdev.fsmdelivery.domain.Delivery;
import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
@Configuration
public class DeliveryQueueListener {

  private final DeliveryService deliveryService;

  @Bean
  public Queue createQueue() {
    return new Queue("delivery.queue", true);
  }

  @RabbitListener(queues = "delivery.queue")
  void messageReceive(final Message<DeliveryActionDTO> message) {
    if (message.getPayload().getEvent().equals("create")) {
      final var delivery = deliveryService.newDelivery(Delivery.builder().build());
      System.out.println("Delivery created: " + delivery);
    } else {
      deliveryService.sendEvent(
          message.getPayload().getId(),
          DeliveryEvent.valueOf(message.getPayload().getEvent().toUpperCase())
      );
    }
  }

}
