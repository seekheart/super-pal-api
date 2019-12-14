package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class BossNotFoundException extends RuntimeException {

  public BossNotFoundException(UUID id) {
    super("Boss id=" + id + " does not exist!");
  }
}
