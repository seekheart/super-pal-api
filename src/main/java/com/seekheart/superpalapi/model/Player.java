package com.seekheart.superpalapi.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"teams"})
public class Player {
  @Id
  @GeneratedValue(generator = "player_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "player_sequence", sequenceName = "player_sequence", allocationSize = 1)
  private Long id;
  private String name;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "player_team",
      joinColumns = { @JoinColumn(name = "player_id")},
      inverseJoinColumns = { @JoinColumn(name = "team_id")}
      )
  private Set<Team> teams;
}
