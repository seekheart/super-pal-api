package com.seekheart.superpalapi.model.error;

public class BossExistsException extends RuntimeException {

  public BossExistsException(String bossName) {
    super("Boss=" + bossName + " already exists in the current raid!");
  }
}
