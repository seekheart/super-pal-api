package com.seekheart.superpalapi.model.web;

import java.util.UUID;
import lombok.Data;

@Data
public class AssignmentRequest {

  private UUID playerId;
  private UUID teamId;
  private UUID bossId;
}
