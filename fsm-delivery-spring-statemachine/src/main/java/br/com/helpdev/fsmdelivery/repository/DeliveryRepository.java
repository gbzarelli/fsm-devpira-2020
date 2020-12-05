package br.com.helpdev.fsmdelivery.repository;

import br.com.helpdev.fsmdelivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
