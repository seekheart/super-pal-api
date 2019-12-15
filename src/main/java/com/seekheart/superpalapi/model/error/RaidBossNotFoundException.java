package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class RaidBossNotFoundException extends RuntimeException {

  public RaidBossNotFoundException(UUID id) {
    super("RaidBoss not found for raid=" + id);
  }
}
