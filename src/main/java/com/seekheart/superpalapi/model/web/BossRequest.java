package com.seekheart.superpalapi.model.web;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BossRequest {

  private UUID id;
  private UUID leagueId;
  private UUID raidId;
  private String name;
  private Long hp;
}
