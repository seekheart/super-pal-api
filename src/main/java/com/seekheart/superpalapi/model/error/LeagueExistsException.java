package com.seekheart.superpalapi.model.error;

public class LeagueExistsException extends RuntimeException {

  public LeagueExistsException(String name) {
    super("League name=" + name + " already exists!");
  }

}
