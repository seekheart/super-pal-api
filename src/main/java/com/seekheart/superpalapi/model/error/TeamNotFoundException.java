package com.seekheart.superpalapi.model.error;

public class TeamNotFoundException extends RuntimeException {

  public TeamNotFoundException(String teamName) {
    super("Could not find team name=" + teamName);
  }

}
