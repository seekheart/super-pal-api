package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class PlayerNotFoundException extends RuntimeException {

  public PlayerNotFoundException(UUID id) {
    super("Player id=" + id.toString() + " not found!");
  }
}
