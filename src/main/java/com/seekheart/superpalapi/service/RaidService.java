package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Boss;
import com.seekheart.superpalapi.model.domain.Raid;
import com.seekheart.superpalapi.model.domain.RaidBoss;
import com.seekheart.superpalapi.model.error.BossNotFoundException;
import com.seekheart.superpalapi.model.error.RaidAlreadyActiveException;
import com.seekheart.superpalapi.model.error.RaidBossNotFoundException;
import com.seekheart.superpalapi.model.error.RaidNotFoundException;
import com.seekheart.superpalapi.model.util.RaidStatusOptions;
import com.seekheart.superpalapi.model.web.RaidRequest;
import com.seekheart.superpalapi.model.web.RaidResponse;
import com.seekheart.superpalapi.repository.BossRepository;
import com.seekheart.superpalapi.repository.RaidBossRepository;
import com.seekheart.superpalapi.repository.RaidRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RaidService {

  private RaidRepository raidRepository;
  private RaidBossRepository raidBossRepository;
  private BossRepository bossRepository;
  private Map<Long, Long> bossHealthLookup;
  private List<String> bossNames;


  private RaidResponse makeResponse(Raid raid) {
    Long tier = raid.getTier();

    List<RaidBoss> raidBosses = raidBossRepository.findByRaidId(raid.getId()).orElseThrow(
        () -> new RaidNotFoundException(raid.getId())
    );

    List<Boss> bosses;

    if (raidBosses.isEmpty()) {
      bosses = makeBossesFromTier(tier);
    } else {
      bosses = raidBosses.stream()
          .map(r -> bossRepository.findById(r.getBossId())
              .orElseThrow(() -> new BossNotFoundException(r.getBossId())))
          .collect(Collectors.toList());
    }

    return RaidResponse.builder()
        .id(raid.getId())
        .bosses(bosses)
        .tier(raid.getTier())
        .state(raid.getState())
        .build();
  }

  private List<Boss> makeBossesFromTier(Long tier) {
    if (bossHealthLookup.isEmpty()) {
      log.info("Bootstrapping boss lookup");
      bossHealthLookup.put(2L, 514000L);
      bossHealthLookup.put(3L, 1177600L);
      bossHealthLookup.put(4L, 2160000L);
      bossHealthLookup.put(5L, 3290000L);
      bossHealthLookup.put(6L, 5845000L);
      bossHealthLookup.put(7L, 58898600L);
      bossHealthLookup.put(8L, 112500000L);
      log.info("Done Bootstrapping {}", bossHealthLookup);
    }

    if (bossNames.isEmpty()) {
      log.info("Bootstrapping boss names");
      bossNames.add("Telekinetic Gorilla Grodd");
      bossNames.add("Captain Cold");
      bossNames.add("Horrific Scarecrow");
      bossNames.add("Doctor Fate");
      bossNames.add("Brainiac Phase 1");
      bossNames.add("Brainiac Phase 2");
      bossNames.add("Brainiac Phase 3");
      bossNames.add("Brainiac Phase 4");
    }

    Long health = bossHealthLookup.get(tier);
    log.info("Retrieved health={} for tier={}", health, tier);

    return bossNames.stream().map(name -> {
          Boss boss = Boss.builder()
              .id(UUID.randomUUID())
              .name(name)
              .health(health)
              .build();

          log.info("Saving new boss={}", boss);
          bossRepository.save(boss);
          return boss;
        }
    ).collect(Collectors.toList());
  }

  public List<RaidResponse> findAll() {
    List<RaidResponse> responses = new ArrayList<>();

    raidRepository.findAll().forEach(r -> {
      RaidResponse response = makeResponse(r);
      responses.add(response);
    });

    return responses;
  }

  public RaidResponse createRaid(RaidRequest request) {
    log.info("Proccessing request={}", request);
    //find out if there is an active raid already (state != end)
    List<Raid> raidFunding = raidRepository.findByState(RaidStatusOptions.FUNDING)
        .orElse(Collections.emptyList());
    List<Raid> raidInProg = raidRepository.findByState(RaidStatusOptions.IN_PROGRESS)
        .orElse(Collections.emptyList());

    if (!raidFunding.isEmpty() || !raidInProg.isEmpty()) {
      log.error("Cannot make a new raid while an active raid is present!");
      throw new RaidAlreadyActiveException();
    }

    log.info("No active raid found, creating raid now!");
    Raid record = Raid.builder()
        .id(UUID.randomUUID())
        .state(request.getState())
        .tier(request.getTier())
        .build();

    log.info("Saving newly requested raid");
    raidRepository.save(record);

    log.info("making new bosses for this raid");
    Long tier = request.getTier();
    List<Boss> bosses = makeBossesFromTier(tier);

    log.info("Registering raid and boss links");
    bosses.forEach(b -> {
      RaidBoss raidBoss = RaidBoss.builder()
          .id(UUID.randomUUID())
          .bossId(b.getId())
          .raidId(record.getId())
          .build();

      log.info("Saving raidBoss record={}", raidBoss);
      raidBossRepository.save(raidBoss);
    });

    return makeResponse(record);
  }

  public void editOne(UUID id, RaidRequest req) {
    Raid raid = raidRepository.findById(id).orElseThrow(() -> new RaidNotFoundException(id));

    log.info("Updating raid state from={} to={}", raid.getState(), req.getState());
    raid.setState(req.getState());

    raidRepository.save(raid);
    log.info("Successfully updated raid status");
  }

  public void deleteOne(UUID id) {
    Raid raid = raidRepository.findById(id).orElseThrow(() -> new RaidNotFoundException(id));
    List<RaidBoss> links = raidBossRepository.findByRaidId(raid.getId()).orElse(null);

    if (links != null && !links.isEmpty()) {
      log.info("Deleting bosses from deprecated raid");
      links.forEach(l -> {
        Boss boss =
            bossRepository.findById(l.getBossId())
                .orElseThrow(() -> new BossNotFoundException(l.getBossId()));
        raidBossRepository.delete(l);
        bossRepository.delete(boss);
      });
      log.info("Successfully deleted all bosses from deprecated raid");
    }

    log.info("Deleting deprecated raid id={}", id);
    raidRepository.delete(raid);

    log.info("Successfully deleted raid");
  }

  public void editBosses(UUID id, RaidRequest request) {
    log.info("Finding raid for id={}", id);
    Raid raid = raidRepository.findById(id).orElseThrow(() -> new RaidNotFoundException(id));

    log.info("Finding raid boss ids for raid={}", raid.getId());
    List<RaidBoss> raidBosses = raidBossRepository.findByRaidId(id).orElse(Collections.emptyList());

    if (raidBosses.isEmpty()) {
      log.error("No raid bosses are present");
      throw new RaidBossNotFoundException(id);
    }

    log.info("Updating health of bosses for raid id={}", id);
    request.getBosses().forEach(b -> bossRepository.save(b));
    log.info("Successfully updated all bosses");
  }
}
