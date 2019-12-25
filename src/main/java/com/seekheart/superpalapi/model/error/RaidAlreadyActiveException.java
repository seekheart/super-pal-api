package com.seekheart.superpalapi.model.error;

public class RaidAlreadyActiveException extends RuntimeException {

  public RaidAlreadyActiveException() {
    super("Only 1 active raid is allowed!");
  }
}
