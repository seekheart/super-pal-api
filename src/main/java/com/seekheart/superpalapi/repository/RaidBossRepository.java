package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.RaidBoss;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaidBossRepository extends CrudRepository<RaidBoss, UUID> {

  public Optional<List<RaidBoss>> findByRaidId(UUID raidId);
}
