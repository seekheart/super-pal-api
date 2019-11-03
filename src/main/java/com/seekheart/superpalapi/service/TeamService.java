package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.Team;
import com.seekheart.superpalapi.repository.TeamRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
  private TeamRepository repository;

  public TeamService(TeamRepository repository) {
    this.repository = repository;
  }

  public Iterable<Team> findAll() {
    return repository.findAll();
  }

  public Team createTeam(Team team) {
    return repository.save(team);
  }

  public Optional<Team> findTeamByName(String teamName) {
    return repository.findTeamByName(teamName);
  }

  public Optional<Team> findById(Long id) {
    return repository.findById(id);
  }
}
