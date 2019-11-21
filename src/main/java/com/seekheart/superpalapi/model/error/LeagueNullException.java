package com.seekheart.superpalapi.model.error;

public class LeagueNullException extends RuntimeException {

  public LeagueNullException() {
    super("League id cannot be null!");
  }
}
