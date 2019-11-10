package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Team;
import com.seekheart.superpalapi.model.web.TeamRequest;
import com.seekheart.superpalapi.model.web.TeamResponse;
import com.seekheart.superpalapi.repository.PlayerTeamRepository;
import com.seekheart.superpalapi.repository.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TeamService {

  private TeamRepository teamRepository;
  private PlayerTeamRepository playerTeamRepository;

  public List<TeamResponse> findAll() {
    Iterable<Team> teams = teamRepository.findAll();
    List<TeamResponse> response = new ArrayList<>();

    teams.forEach(t -> response.add(new TeamResponse(t)));

    return response;
  }

  public TeamResponse createOne(TeamRequest request) {
    Team teamRecord = Team.builder()
        .id(UUID.randomUUID())
        .name(request.getName())
        .build();

    Team savedRecord = teamRepository.save(teamRecord);

    return new TeamResponse(savedRecord);
  }

  public TeamResponse editOne(UUID id, TeamRequest request) throws Exception {
    Team oldRecord = teamRepository.findById(id).orElse(null);

    if (oldRecord == null) {
      log.error("Bad Request! team = {} does not exist!", id);
      throw new Exception("Bad Request team does not exist!");
    }

    if (request.getName() == null) {
      log.error("Team name cannot be blank!");
      throw new Exception("Bad Request team name cannot be blank!");
    }

    Team newRecord = Team.builder()
        .id(oldRecord.getId())
        .name(request.getName())
        .build();

    Team savedRecord = teamRepository.save(newRecord);

    return new TeamResponse(savedRecord);
  }

  public Boolean deleteOne(UUID id) {
    try {
      teamRepository.delete(teamRepository.findById(id).orElse(null));
    } catch (Exception e) {
      log.error("Could not delete team = {}", id);
      return false;
    }

    return true;
  }
}
