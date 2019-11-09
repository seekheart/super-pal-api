package com.seekheart.superpalapi.model.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class TeamBossAssignment {

  private String boss;
  private String team;
}
