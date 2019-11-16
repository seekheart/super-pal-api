package com.seekheart.superpalapi.model.error;


public class LeagueNotFoundException extends RuntimeException {

  public LeagueNotFoundException(String name) {
    super("League name=" + name + " not found!");
  }
}
