package com.seekheart.superpalapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_sequence")
  @SequenceGenerator(allocationSize = 1, sequenceName = "team_sequence", name = "team_sequence")
  private Long id;
  private String name;


  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "teams", cascade = CascadeType.PERSIST)
  @JsonIgnore
  private Set<Player> players;
}
