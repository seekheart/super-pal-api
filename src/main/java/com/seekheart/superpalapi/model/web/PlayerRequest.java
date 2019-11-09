package com.seekheart.superpalapi.model.web;

import java.util.UUID;
import lombok.Data;

@Data
public class PlayerRequest {

  private UUID discordId;
  private String name;
  private String league;
  private String role;
}
