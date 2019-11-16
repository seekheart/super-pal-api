package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Assignment;
import com.seekheart.superpalapi.model.domain.Boss;
import com.seekheart.superpalapi.model.domain.League;
import com.seekheart.superpalapi.model.domain.Player;
import com.seekheart.superpalapi.model.domain.PlayerTeam;
import com.seekheart.superpalapi.model.domain.Team;
import com.seekheart.superpalapi.model.error.LeagueNotFoundException;
import com.seekheart.superpalapi.model.error.PlayerNotFoundException;
import com.seekheart.superpalapi.model.error.TeamNotFoundException;
import com.seekheart.superpalapi.model.error.TeamNullException;
import com.seekheart.superpalapi.model.web.PlayerRequest;
import com.seekheart.superpalapi.model.web.PlayerResponse;
import com.seekheart.superpalapi.model.web.TeamBossAssignment;
import com.seekheart.superpalapi.repository.AssignmentRepository;
import com.seekheart.superpalapi.repository.BossRepository;
import com.seekheart.superpalapi.repository.LeagueRepository;
import com.seekheart.superpalapi.repository.PlayerRepository;
import com.seekheart.superpalapi.repository.PlayerTeamRepository;
import com.seekheart.superpalapi.repository.TeamRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
  private LeagueRepository leagueRepository;
  private PlayerTeamRepository playerTeamRepository;

  public List<PlayerResponse> findAll() {
    Iterable<Player> players = playerRepository.findAll();
    List<PlayerResponse> response = new ArrayList<>();
    players.forEach(p -> {
      PlayerResponse player = makeResponse(p);
      response.add(player);
    });

    return response;

  }

  public PlayerResponse findOne(UUID id) {
    Player player = playerRepository.findById(id).orElse(null);

    if (player == null) {
      log.error("Player = {} does not exist", id);
      throw new PlayerNotFoundException(id);
    }

    return makeResponse(player);
  }


  public PlayerResponse createPlayer(PlayerRequest playerRequest) {
    log.info("Registering Player = {} with discord id = {}",
        playerRequest.getName(),
        playerRequest.getDiscordId());

    UUID uuid = UUID.randomUUID();
    Player player = Player.builder()
        .id(uuid)
        .discordId(playerRequest.getDiscordId())
        .name(playerRequest.getName())
        .role(playerRequest.getRole())
        .build();

    String playerLeague = playerRequest.getLeague();
    log.info("Finding player league={}", playerLeague);
    if (!playerLeague.equals("")) {
      League league;
      league = leagueRepository.findByName(playerLeague).orElse(null);

      if (league != null) {
        log.debug("Found league = {} for player = {}", league.getName(), player.getName());
        player.setLeagueId(league.getId());
      }
    }

    Player savedPlayer = playerRepository.save(player);

    return makeResponse(savedPlayer);
  }

  public PlayerResponse editPlayer(UUID id, PlayerRequest player) {
    Player playerRecord =
        playerRepository.findById(id).orElse(null);

    if (playerRecord == null) {
      log.error("No player found with id = {}", id);
      throw new PlayerNotFoundException(id);
    }

    UUID leagueRegistered = null;

    if (player.getLeague() != null) {
      League league = leagueRepository.findByName(player.getLeague()).orElse(null);

      if (league == null) {
        log.error("League = {} does not exist!", player.getLeague());
        throw new LeagueNotFoundException(player.getLeague());
      }

      leagueRegistered = league.getId();
    }

    playerRecord.setRole(player.getRole());
    playerRecord.setLeagueId(leagueRegistered);
    playerRecord.setName(player.getName());

    Player savedPlayer = playerRepository.save(playerRecord);
    log.info("Successfully updated player id = {}", player.getDiscordId());
    return makeResponse(savedPlayer);
  }

  public void deletePlayer(UUID id) {
    Player playerRecord = playerRepository.findById(id).orElse(null);

    if (playerRecord == null) {
      log.error("Invalid player id = {}", id);
      throw new PlayerNotFoundException(id);
    }

    try {
      playerRepository.delete(playerRecord);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    log.info("Successfully deleted player id={}", id);
  }


  public void editTeam(UUID playerId, PlayerRequest playerRequest) {
    List<String> teams = playerRequest.getTeams();
    Player player = playerRepository.findById(playerId).orElse(null);

    if (player == null) {
      log.error("Player Id={} does not exist!", playerId);
      throw new PlayerNotFoundException(playerId);
    } else if (teams.size() == 0 || teams == null) {
      log.error("No teams to save for player id={}", playerId);
      throw new TeamNullException(playerId);
    }

    for (String t : teams) {
      Team team = teamRepository.findByName(t).orElse(null);
      if (team == null) {
        log.error("Team= {} could not found", t);
        throw new TeamNotFoundException(t);
      }

      PlayerTeam playerTeam =
          playerTeamRepository.findByPlayerIdAndTeamId(playerId, team.getId()).orElse(null);

      // no point in persisting if team for that player exists
      if (playerTeam != null) {
        log.info("player={} already associated with team={}", playerTeam.getId(), t);
      } else {
        PlayerTeam record = PlayerTeam
            .builder()
            .id(UUID.randomUUID())
            .playerId(player.getId())
            .teamId(team.getId())
            .build();

        log.info(
            "Saving player team record id={} playerId={} teamId={}",
            record.getId(),
            record.getPlayerId(), record.getTeamId()
        );
        playerTeamRepository.save(record);
      }
    }
  }

  private List<TeamBossAssignment> findPlayerAssignments(List<Assignment> assignments) {
    return assignments.stream().map(assignment -> {
      Boss boss = bossRepository.findById(assignment.getBossId()).orElse(null);
      Team team = teamRepository.findById(assignment.getTeamId()).orElse(null);
      return new TeamBossAssignment(boss.getName(), team.getName());
    }).collect(Collectors.toList());
  }

  private HashSet<String> getTeamNames(HashSet<Team> teams) {
    return teams.stream().map(Team::getName).collect(Collectors.toCollection(HashSet::new));
  }

  private HashSet<Team> findPlayerTeams(Player player) {
    List<PlayerTeam> lookup = playerTeamRepository.findAllByPlayerId(player.getId()).orElse(null);

    if (lookup == null || lookup.size() < 1) {
      log.debug("Player = {} has no teams registered", player.getId());
      return new HashSet<>();
    }

    return lookup.stream()
        .map(l -> teamRepository.findById(l.getTeamId()).orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toCollection(HashSet::new));
  }

  private PlayerResponse makeResponse(Player player) {
    List<Assignment> assignments = assignmentRepository.findAllByPlayerId(player.getId());
    List<TeamBossAssignment> targets = findPlayerAssignments(assignments);
    HashSet<String> teams = getTeamNames(findPlayerTeams(player));

    return PlayerResponse.builder()
        .id(player.getId())
        .userName(player.getName())
        .assignments(new HashSet<>(targets))
        .teams(teams)
        .build();
  }

}
