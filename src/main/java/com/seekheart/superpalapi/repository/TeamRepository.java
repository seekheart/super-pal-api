package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.Team;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, UUID> {

  Optional<Team> findByName(String name);
}

