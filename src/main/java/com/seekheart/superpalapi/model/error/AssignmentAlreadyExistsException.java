package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class AssignmentAlreadyExistsException extends RuntimeException {

  public AssignmentAlreadyExistsException(UUID playerId, UUID bossId, UUID teamId) {
    super(
        String.format(
            "Could not find assignment with player id=%s boss id = %s team id= %s",
            playerId,
            bossId,
            teamId
        )
    );
  }
}
