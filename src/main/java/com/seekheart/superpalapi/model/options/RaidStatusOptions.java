package com.seekheart.superpalapi.model.options;

import lombok.Getter;

@Getter
public enum RaidStatusOptions {
  STARTED("started"),
  FUNDING("funding"),
  IN_PROGRESS("in progress"),
  ENDED("ended");
  private String state;

  RaidStatusOptions(String state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return state;
  }
}
