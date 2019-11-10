package com.seekheart.superpalapi.model.web;

import com.seekheart.superpalapi.model.domain.Team;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamResponse {

  private UUID id;
  private String name;

  public TeamResponse(Team team) {
    this.id = team.getId();
    this.name = team.getName();
  }
}
