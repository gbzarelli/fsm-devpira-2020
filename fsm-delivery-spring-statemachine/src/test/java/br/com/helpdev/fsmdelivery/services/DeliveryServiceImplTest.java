package br.com.helpdev.fsmdelivery.services;

import br.com.helpdev.fsmdelivery.domain.Delivery;
import br.com.helpdev.fsmdelivery.domain.DeliveryEvent;
import br.com.helpdev.fsmdelivery.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DeliveryServiceImplTest {

  @Autowired
  DeliveryService deliveryService;

  @Autowired
  DeliveryRepository deliveryRepository;

  Delivery delivery;

  @BeforeEach
  void setUp() {
    delivery = Delivery.builder().build();
  }

  @Transactional
  @Test
  void newDelivery() {
    Delivery savedDelivery = deliveryService.newDelivery(delivery);

    System.out.println("Should be NEW");
    System.out.println(savedDelivery.getState());

    deliveryService.sendEvent(delivery.getId(), DeliveryEvent.PICKED_UP);

    try {
      Thread.sleep(100_000);
    } catch (Exception e) {
    }
  }

}