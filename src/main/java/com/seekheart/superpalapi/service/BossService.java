package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Boss;
import com.seekheart.superpalapi.model.error.BossExistsException;
import com.seekheart.superpalapi.model.error.BossNotFoundException;
import com.seekheart.superpalapi.model.error.GenericBadRequest;
import com.seekheart.superpalapi.model.error.LeagueNotFoundException;
import com.seekheart.superpalapi.model.error.RaidNotFoundException;
import com.seekheart.superpalapi.model.web.BossRequest;
import com.seekheart.superpalapi.model.web.BossResponse;
import com.seekheart.superpalapi.repository.BossRepository;
import com.seekheart.superpalapi.repository.LeagueRepository;
import com.seekheart.superpalapi.repository.RaidRepository;
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
public class BossService {

  private BossRepository bossRepository;
  private RaidRepository raidRepository;
  private LeagueRepository leagueRepository;

  public List<BossResponse> findAll() {
    List<BossResponse> response = new ArrayList<>();
    bossRepository.findAll().forEach(b -> {
      response.add(new BossResponse(b));
    });
    return response;
  }

  public BossResponse findOne(UUID id) {
    return new BossResponse(
        bossRepository.findById(id).orElseThrow(() -> new BossNotFoundException(id))
    );
  }

  public List<BossResponse> findByRaid(UUID raidId) {
    if (raidId == null || !checkRaidExists(raidId)) {
      throw new RaidNotFoundException(raidId);
    }

    List<Boss> bosses = new ArrayList<>(bossRepository.findByRaidId(raidId)
        .orElse(Collections.emptyList()));

    return bosses.stream()
        .map(BossResponse::new)
        .collect(Collectors.toList());

  }

  public BossResponse addBossToRaid(BossRequest request) {
    validateRequest(request);

    Boss record = Boss.builder()
        .id(UUID.randomUUID())
        .hp(request.getHp())
        .name(request.getName())
        .raidId(request.getRaidId())
        .build();

    Boss savedRecord = bossRepository.save(record);
    log.info("Successfully created boss={} for raid id={}", record.getName(), record.getRaidId());

    return new BossResponse(savedRecord);
  }

  public BossResponse editOne(UUID id, BossRequest request) {
    Boolean isValid =
        checkLeagueExists(request.getLeagueId()) && checkRaidExists(request.getRaidId());

    if (!isValid) {
      throw new GenericBadRequest();
    }

    Boss record = bossRepository.findById(id).orElse(null);

    if (record == null) {
      throw new BossNotFoundException(request.getName());
    }

    record.setHp(request.getHp());
    record.setName(request.getName());

    Boss saved = bossRepository.save(record);

    return new BossResponse(saved);
  }


  public void deleteOne(UUID id) {
    bossRepository.delete(
        bossRepository.findById(id).orElseThrow(() -> new BossNotFoundException(id))
    );
  }

  private void validateRequest(BossRequest request) {
    if (!checkLeagueExists(request.getLeagueId())) {
      throw new LeagueNotFoundException(request.getLeagueId());
    }
    if (!checkRaidExists(request.getRaidId())) {
      throw new RaidNotFoundException(request.getRaidId());
    }
    if (checkBossExistsInRaid(request.getRaidId(), request.getName())) {
      throw new BossExistsException(request.getName());
    }
  }


  private Boolean checkLeagueExists(UUID leagueId) {
    return leagueRepository.findById(leagueId).isPresent();
  }

  private Boolean checkRaidExists(UUID raidId) {
    return raidRepository.findById(raidId).isPresent();
  }

  private Boolean checkBossExistsInRaid(UUID raidId, String bossName) {
    return bossRepository.findByRaidIdAndName(raidId, bossName).isPresent();
  }

}
