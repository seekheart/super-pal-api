package com.seekheart.superpalapi.model.web;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponse {

  private UUID id;
  private String player;
  private String team;
  private String boss;
}
