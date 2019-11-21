package com.seekheart.superpalapi.model.web;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueRequest {

  private UUID id;
  private String name;
  private String playerName;
}
