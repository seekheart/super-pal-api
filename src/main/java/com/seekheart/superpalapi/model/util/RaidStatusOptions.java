package com.seekheart.superpalapi.model.util;

import lombok.Getter;

@Getter
public enum RaidStatusOptions {
  FUNDING("funding"), IN_PROGRESS("in progress"), ENDED("ended");
  private final String value;

  RaidStatusOptions(String value) {
    this.value = value;
  }
}
