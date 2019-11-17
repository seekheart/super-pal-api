package com.seekheart.superpalapi.model.web;

import lombok.Data;

@Data
public class PlayerRequest {

  private String discordId;
  private String name;
  private String league;
  private String role;
  private String team;
}
