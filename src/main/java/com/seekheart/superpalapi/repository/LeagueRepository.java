package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.League;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends CrudRepository<League, UUID> {

  Optional<League> findByName(String name);
}
