package com.seekheart.superpalapi.model.error;

public class BossNullException extends RuntimeException {

  public BossNullException() {
    super("Boss id cannot be null!");
  }
}
