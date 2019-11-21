package com.seekheart.superpalapi.model.error;

public class LeagueNameNullException extends RuntimeException {

  public LeagueNameNullException() {
    super("League name cannot be null or empty");
  }
}
