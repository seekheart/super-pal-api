package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class NoActiveRaidException extends RuntimeException {

  public NoActiveRaidException(UUID uuid) {
    super("No active raid present for id=" + uuid);
  }
}
