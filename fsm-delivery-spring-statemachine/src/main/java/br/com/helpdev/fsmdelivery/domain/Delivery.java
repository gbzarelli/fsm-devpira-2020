package br.com.helpdev.fsmdelivery.domain;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {

  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private DeliveryState state;

  private ZonedDateTime outForDeliveryDateTime;
}
