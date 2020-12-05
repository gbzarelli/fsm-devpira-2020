package br.com.helpdev.fsmdelivery.entrypoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryActionDTO {
  private Long id;
  private String event;
}
