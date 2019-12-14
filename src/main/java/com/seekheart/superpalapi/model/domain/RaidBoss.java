package com.seekheart.superpalapi.model.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RaidBoss {

  @Id
  private UUID id;
  @Column
  private UUID raidId;
  @Column
  private UUID bossId;
}
