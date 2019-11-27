package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class RaidNotFoundException extends RuntimeException {

  public RaidNotFoundException(UUID id) {
    super("Raid id=" + id + " not found!");
  }
}
