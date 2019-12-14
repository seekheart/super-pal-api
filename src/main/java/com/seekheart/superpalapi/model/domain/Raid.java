package com.seekheart.superpalapi.model.domain;

import com.seekheart.superpalapi.model.util.RaidStatusOptions;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Raid {

  @Id
  private UUID id;
  @Column
  private Long tier;
  @Column
  private RaidStatusOptions state;
}
