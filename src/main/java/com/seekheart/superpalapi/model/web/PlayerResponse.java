package com.seekheart.superpalapi.model.web;

import java.util.HashSet;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PlayerResponse {

  private UUID id;
  private String userName;
  @Builder.Default
  private HashSet<String> teams = new HashSet<>();
  @Builder.Default
  private HashSet<TeamBossAssignment> assignments = new HashSet<>();
}
