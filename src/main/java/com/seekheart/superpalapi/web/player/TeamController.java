package com.seekheart.superpalapi.web.player;

import com.seekheart.superpalapi.model.Team;
import com.seekheart.superpalapi.service.TeamService;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
public class TeamController {
  private TeamService service;

  public TeamController(TeamService service) {
    this.service = service;
  }

  @GetMapping
  public Iterable<Team> getAllTeams() {
    return service.findAll();
  }

  @PostMapping
  public Team createTeam(Team team) {
    return service.createTeam(team);
  }

  @GetMapping("/{id}")
  public Optional<Team> findOne(@PathVariable Long id) {
    return service.findById(id);
  }
}
