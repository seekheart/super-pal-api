package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.domain.Boss;
import com.seekheart.superpalapi.model.domain.RaidBoss;
import com.seekheart.superpalapi.model.error.BossNotFoundException;
import com.seekheart.superpalapi.model.error.RaidBossNotFoundException;
import com.seekheart.superpalapi.model.web.BossRequest;
import com.seekheart.superpalapi.model.web.BossResponse;
import com.seekheart.superpalapi.repository.BossRepository;
import com.seekheart.superpalapi.repository.RaidBossRepository;
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
public class BossService {

  private BossRepository bossRepository;
  private RaidBossRepository raidBossRepository;

  public List<BossResponse> findBossesByRaidId(UUID raidId) {
    List<UUID> bossIds = raidBossRepository.findByRaidId(raidId)
        .orElseThrow(() -> new RaidBossNotFoundException(raidId))
        .stream().map(RaidBoss::getBossId).collect(Collectors.toList());

    log.info("Successfully fetched all the boss ids={}", bossIds);

    List<BossResponse> response = new ArrayList<>();
    bossIds.forEach(id -> {
      Boss boss = bossRepository.findById(id)
          .orElseThrow(() -> new BossNotFoundException(id));

      response.add(new BossResponse(boss));
    });

    return response;
  }

  public void editBoss(UUID id, BossRequest request) {
    Boss boss = bossRepository.findById(id).orElseThrow(() -> new BossNotFoundException(id));
    Long updatedHealth = boss.getHealth() - request.getDamage();

    if (updatedHealth < 0) {
      updatedHealth = 0L;
    }

    boss.setHealth(updatedHealth);
    log.info("Set boss={} health={}", boss.getName(), updatedHealth);

    bossRepository.save(boss);
  }
}
