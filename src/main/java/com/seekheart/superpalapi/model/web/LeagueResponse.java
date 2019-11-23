package com.seekheart.superpalapi.model.web;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueResponse {

  private UUID id;
  private String name;
  private List<String> players;
}
