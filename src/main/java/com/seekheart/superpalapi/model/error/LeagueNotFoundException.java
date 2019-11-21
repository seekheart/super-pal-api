package com.seekheart.superpalapi.model.error;


import java.util.UUID;

public class LeagueNotFoundException extends RuntimeException {

  public LeagueNotFoundException(UUID id) {
    super("League id=" + id.toString() + " not found!");
  }

  public LeagueNotFoundException(String name) {
    super("League=" + name + " not found!");
  }
}
