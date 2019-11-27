package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Raid;
import com.seekheart.superpalapi.model.error.RaidActiveException;
import com.seekheart.superpalapi.model.error.RaidNotFoundException;
import com.seekheart.superpalapi.model.options.RaidStatusOptions;
import com.seekheart.superpalapi.model.web.RaidRequest;
import com.seekheart.superpalapi.model.web.RaidResponse;
import com.seekheart.superpalapi.repository.BossRepository;
import com.seekheart.superpalapi.repository.RaidRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RaidService {

  private BossRepository bossRepository;
  private RaidRepository raidRepository;

  public List<RaidResponse> findAll() {
    List<RaidResponse> raids = new ArrayList<>();
    raidRepository.findAll().forEach(r -> raids.add(new RaidResponse(r)));

    return raids;
  }

  public RaidResponse findOne(UUID id) {
    Raid raid = raidRepository.findById(id)
        .orElseThrow(() -> new RaidNotFoundException(id));

    return new RaidResponse(raid);
  }

  public RaidResponse CreateOne(RaidRequest request) {
    Boolean raidExists =
        raidRepository.findByLeagueId(request.getLeagueId())
            .filter(r -> r.getStatus() != RaidStatusOptions.ENDED)
            .isPresent();

    if (raidExists) {
      throw new RaidActiveException();
    }

    Raid record = Raid
        .builder()
        .id(UUID.randomUUID())
        .leagueId(request.getLeagueId())
        .rank(request.getTier())
        .status(RaidStatusOptions.STARTED)
        .build();

    Raid saved = raidRepository.save(record);

    log.info("Successfully started new league raid for league id={} raid id={}",
        saved.getLeagueId(), saved.getId());

    return new RaidResponse(saved);
  }
}
