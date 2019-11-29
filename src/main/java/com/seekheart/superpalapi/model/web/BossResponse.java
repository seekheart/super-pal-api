package com.seekheart.superpalapi.model.web;

import com.seekheart.superpalapi.model.domain.Boss;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BossResponse {

  private UUID id;
  private UUID raidId;
  private String name;
  private Long hp;

  public BossResponse(Boss boss) {
    this.id = boss.getId();
    this.raidId = boss.getRaidId();
    this.hp = boss.getHp();
    this.name = boss.getName();
  }
}
