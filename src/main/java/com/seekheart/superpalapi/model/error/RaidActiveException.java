package com.seekheart.superpalapi.model.error;

public class RaidActiveException extends RuntimeException {

  public RaidActiveException() {
    super("There is already an active raid happening, cannot start a new raid!");
  }
}
