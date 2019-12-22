package com.seekheart.superpalapi.model.error;

public class BadRaidRequest extends RuntimeException {

  public BadRaidRequest() {
    super("Cannot have raid tier lower than 2 or greater than 8!");
  }
}
