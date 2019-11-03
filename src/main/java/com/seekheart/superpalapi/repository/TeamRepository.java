package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.Team;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
  Optional<Team> findTeamByName(String name);
}
