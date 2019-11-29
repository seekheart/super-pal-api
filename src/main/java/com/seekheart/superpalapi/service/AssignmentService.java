package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Assignment;
import com.seekheart.superpalapi.model.error.AssignmentAlreadyExistsException;
import com.seekheart.superpalapi.model.error.AssignmentNotFoundException;
import com.seekheart.superpalapi.model.error.BossNotFoundException;
import com.seekheart.superpalapi.model.error.GenericBadRequest;
import com.seekheart.superpalapi.model.error.PlayerNotFoundException;
import com.seekheart.superpalapi.model.error.TeamNotFoundException;
import com.seekheart.superpalapi.model.web.AssignmentRequest;
import com.seekheart.superpalapi.model.web.AssignmentResponse;
import com.seekheart.superpalapi.repository.AssignmentRepository;
import com.seekheart.superpalapi.repository.BossRepository;
import com.seekheart.superpalapi.repository.PlayerRepository;
import com.seekheart.superpalapi.repository.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AssignmentService {

  private AssignmentRepository assignmentRepository;
  private PlayerRepository playerRepository;
  private BossRepository bossRepository;
  private TeamRepository teamRepository;

  private AssignmentResponse makeResponse(Assignment assignment) {
    if (assignment == null) {
      return null;
    }
    String teamName = teamRepository.findById(assignment.getTeamId())
        .orElseThrow(() -> new TeamNotFoundException(assignment.getTeamId())).getName();
    String bossName = bossRepository.findById(assignment.getBossId())
        .orElseThrow(() -> new BossNotFoundException(assignment.getBossId())).getName();
    String playerName = playerRepository.findById(assignment.getPlayerId())
        .orElseThrow(() -> new PlayerNotFoundException(assignment.getPlayerId())).getName();

    return AssignmentResponse.builder()
        .id(assignment.getId())
        .boss(bossName)
        .player(playerName)
        .team(teamName)
        .build();
  }

  public List<AssignmentResponse> findAll() {
    List<AssignmentResponse> response = new ArrayList<>();
    assignmentRepository.findAll().forEach(a -> response.add(makeResponse(a)));

    return response;
  }


  public AssignmentResponse findById(UUID id) {
    return
        makeResponse(
            assignmentRepository.findById(id).orElseThrow(() -> new AssignmentNotFoundException(id))
        );
  }

  public AssignmentResponse saveOne(AssignmentRequest request) {
    validateRequest(request);

    Boolean doesExist = assignmentRepository.findByPlayerIdAndBossIdAndTeamId(
        request.getPlayerId(),
        request.getBossId(),
        request.getTeamId()
    ).isPresent();

    if (doesExist) {
      throw new AssignmentAlreadyExistsException(
          request.getPlayerId(),
          request.getBossId(),
          request.getTeamId()
      );
    }

    Assignment record = Assignment.builder()
        .id(UUID.randomUUID())
        .bossId(request.getBossId())
        .playerId(request.getPlayerId())
        .teamId(request.getTeamId())
        .build();

    Assignment saved = assignmentRepository.save(record);

    return makeResponse(saved);
  }


  private void validateRequest(AssignmentRequest request) {
    if (request.getBossId() == null || request.getPlayerId() == null
        || request.getTeamId() == null) {
      throw new GenericBadRequest();
    }
  }

  public List<AssignmentResponse> findByPlayer(UUID playerId) {
    List<Assignment> assignments = assignmentRepository.findAllByPlayerId(playerId);

    return assignments.stream().map(this::makeResponse).collect(Collectors.toList());
  }

  public AssignmentResponse editOne(UUID id, AssignmentRequest request) {
    validateRequest(request);

    Assignment record = assignmentRepository.findById(id).orElse(null);

    if (record == null) {
      throw new AssignmentNotFoundException(id);
    }

    record.setBossId(request.getBossId());
    record.setPlayerId(request.getPlayerId());
    record.setTeamId(request.getTeamId());

    Assignment saved = assignmentRepository.save(record);
    return makeResponse(saved);
  }

  public void deleteOne(UUID id) {
    assignmentRepository.delete(
        assignmentRepository.findById(id).orElseThrow(() -> new AssignmentNotFoundException(id))
    );
  }
}
