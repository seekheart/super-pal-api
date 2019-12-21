package com.seekheart.superpalapi.model.web;

import com.seekheart.superpalapi.model.domain.Boss;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BossResponse {

  private UUID id;
  private String bossName;
  private Long bossHealth;
  private Boolean isDead;

  public BossResponse(Boss boss) {
    this.id = boss.getId();
    this.bossHealth = boss.getHealth();
    this.bossName = boss.getName();
    this.isDead = boss.getHealth() <= 0;
  }
}
