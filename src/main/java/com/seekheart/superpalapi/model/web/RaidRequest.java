package com.seekheart.superpalapi.model.web;

import com.seekheart.superpalapi.model.domain.Boss;
import com.seekheart.superpalapi.model.util.RaidStatusOptions;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaidRequest {

  private Long tier;
  private RaidStatusOptions
      state;
  private List<Boss> bosses;
}
