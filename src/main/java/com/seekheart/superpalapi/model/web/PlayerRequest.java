package com.seekheart.superpalapi.model.web;

import java.util.List;
import lombok.Data;

@Data
public class PlayerRequest {

  private String discordId;
  private String name;
  private String league;
  private String role;
  private List<String> teams;
}
