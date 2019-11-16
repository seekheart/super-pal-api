package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class PlayerExistsException extends RuntimeException {

  public PlayerExistsException(UUID id) {
    super("Player already exists id=" + id);
  }
}
