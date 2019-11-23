package com.seekheart.superpalapi.model.web;

import com.seekheart.superpalapi.model.options.RaidStatusOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaidRequest {

  private Long tier;
  private RaidStatusOptions status;
}
