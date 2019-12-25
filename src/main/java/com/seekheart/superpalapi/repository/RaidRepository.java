package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.Raid;
import com.seekheart.superpalapi.model.util.RaidStatusOptions;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaidRepository extends CrudRepository<Raid, UUID> {

  public Optional<List<Raid>> findByState(RaidStatusOptions state);
}
