package com.seekheart.superpalapi.model.web;

import com.seekheart.superpalapi.model.domain.Boss;
import com.seekheart.superpalapi.model.util.RaidStatusOptions;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RaidResponse {

  private UUID id;
  private Long tier;
  private RaidStatusOptions state;
  private List<Boss> bosses;
}
