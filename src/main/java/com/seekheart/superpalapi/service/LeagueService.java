package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.League;
import com.seekheart.superpalapi.model.domain.Player;
import com.seekheart.superpalapi.model.error.LeagueExistsException;
import com.seekheart.superpalapi.model.error.LeagueNameNullException;
import com.seekheart.superpalapi.model.error.LeagueNotFoundException;
import com.seekheart.superpalapi.model.error.LeagueNullException;
import com.seekheart.superpalapi.model.web.LeagueRequest;
import com.seekheart.superpalapi.model.web.LeagueResponse;
import com.seekheart.superpalapi.repository.LeagueRepository;
import com.seekheart.superpalapi.repository.PlayerRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class LeagueService {

  private PlayerRepository playerRepository;
  private LeagueRepository leagueRepository;

  public List<LeagueResponse> findAll() {
    List<LeagueResponse> response = new ArrayList<>();
    leagueRepository.findAll().forEach(l -> {
      response.add(makeResponse(l));
    });

    return response;
  }


  public LeagueResponse createLeague(LeagueRequest request) {
    if (request.getName() == null || request.getName().equals("")) {
      log.error("Cannot have league name be null or empty");
      throw new LeagueNameNullException();
    }
    League league = leagueRepository.findByName(request.getName()).orElse(null);

    if (league != null) {
      throw new LeagueExistsException(request.getName());
    }

    League newLeague = League.builder()
        .id(UUID.randomUUID())
        .name(request.getName())
        .build();

    log.info("Saving new league={} with id={}", newLeague.getName(), newLeague.getId());

    League savedLeague = leagueRepository.save(newLeague);
    log.info("Saved league successfully!");

    return makeResponse(savedLeague);

  }

  public LeagueResponse editLeague(UUID leagueId, LeagueRequest request) {
    if (leagueId == null) {
      log.error("League id cannot be null!");
      throw new LeagueNullException();
    }
    if (request.getName() == null || request.getName().equals("")) {
      log.error("Cannot have change league name to null/empty!");
      throw new LeagueNameNullException();
    }

    League record = leagueRepository.findById(leagueId).orElse(null);

    if (record == null) {
      log.error("League id={} does not exist!", leagueId);
      throw new LeagueNotFoundException(leagueId);
    }

    record.setName(request.getName());

    return makeResponse(leagueRepository.save(record));
  }

  private LeagueResponse makeResponse(League record) {
    List<String> players = playerRepository.findByLeagueId(record.getId())
        .orElse(Collections.emptyList())
        .stream()
        .map(Player::getName)
        .collect(Collectors.toList());

    return LeagueResponse.builder()
        .id(record.getId())
        .name(record.getName())
        .players(players)
        .build();


  }

  public LeagueResponse findOne(UUID id) {
    League league = leagueRepository.findById(id).orElse(null);

    if (league == null) {
      throw new LeagueNotFoundException(id);
    }

    return makeResponse(league);
  }

  public void deleteOne(UUID id) {
    League league = leagueRepository.findById(id).orElse(null);

    if (league == null) {
      throw new LeagueNotFoundException(id);
    }

    leagueRepository.delete(league);

  }
}
