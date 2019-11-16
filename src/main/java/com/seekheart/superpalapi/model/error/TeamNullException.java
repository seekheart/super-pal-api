package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class TeamNullException extends RuntimeException {

  public TeamNullException(UUID playerId) {
    super("No teams found for player id=" + playerId);
  }

}
