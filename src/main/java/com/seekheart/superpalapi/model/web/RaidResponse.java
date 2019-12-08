package com.seekheart.superpalapi.model.web;

import com.seekheart.superpalapi.model.domain.Raid;
import com.seekheart.superpalapi.model.options.RaidStatusOptions;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaidResponse {

  private UUID id;
  private Long tier;
  private RaidStatusOptions status;
  private UUID leagueId;

  public RaidResponse(Raid raid) {
    this.id = raid.getId();
    this.tier = raid.getRank();
    this.status = raid.getStatus();
    this.leagueId = raid.getLeagueId();
  }
}
