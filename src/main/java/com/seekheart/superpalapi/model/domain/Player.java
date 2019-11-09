package com.seekheart.superpalapi.model.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

  @Id
  private UUID id;
  private UUID discordId;
  private String name;
  private UUID leagueId;
  private String role;
}
