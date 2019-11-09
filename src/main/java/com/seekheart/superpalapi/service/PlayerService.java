package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Assignment;
import com.seekheart.superpalapi.model.domain.Boss;
import com.seekheart.superpalapi.model.domain.Player;
import com.seekheart.superpalapi.model.domain.Team;
import com.seekheart.superpalapi.model.web.PlayerResponse;
import com.seekheart.superpalapi.model.web.TeamBossAssignment;
import com.seekheart.superpalapi.repository.AssignmentRepository;
import com.seekheart.superpalapi.repository.BossRepository;
import com.seekheart.superpalapi.repository.PlayerRepository;
import com.seekheart.superpalapi.repository.TeamRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerService {

  private PlayerRepository playerRepository;
  private AssignmentRepository assignmentRepository;
  private BossRepository bossRepository;
  private TeamRepository teamRepository;

  public List<PlayerResponse> findAll() {
    Iterable<Player> players = playerRepository.findAll();
    List<PlayerResponse> response = new ArrayList<>();
    players.forEach(p -> {
      List<Assignment> assignments = assignmentRepository.findAllByPlayerId(p.getId());
      List<TeamBossAssignment> targets = findPlayerAssignments(assignments);
      PlayerResponse player = PlayerResponse.builder()
          .id(p.getDiscordId())
          .userName(p.getName())
          .assignments(new HashSet<>(targets))
          .build();
      response.add(player);
    });

    return response;

  }

  private List<TeamBossAssignment> findPlayerAssignments(List<Assignment> assignments) {
    return assignments.stream().map(assignment -> {
      Boss boss = bossRepository.findById(assignment.getBossId()).orElse(null);
      Team team = teamRepository.findById(assignment.getTeamId()).orElse(null);
      return new TeamBossAssignment(boss.getName(), team.getName());
    }).collect(Collectors.toList());
  }


}
