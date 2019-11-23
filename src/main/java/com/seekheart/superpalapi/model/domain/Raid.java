package com.seekheart.superpalapi.model.domain;

import com.seekheart.superpalapi.model.options.RaidStatusOptions;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Raid {

  @Id
  private UUID id;
  private Long rank;
  @Enumerated(EnumType.STRING)
  private RaidStatusOptions status;
}
