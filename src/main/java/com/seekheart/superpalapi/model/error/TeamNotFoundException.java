package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class TeamNotFoundException extends RuntimeException {

  public TeamNotFoundException(String teamName) {
    super("Could not find team name=" + teamName);
  }

  public TeamNotFoundException(UUID id) {
    super("Could not find team id=" + id);
  }

}
