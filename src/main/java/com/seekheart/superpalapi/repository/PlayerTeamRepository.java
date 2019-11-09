package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.PlayerTeam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerTeamRepository extends CrudRepository<PlayerTeam, UUID> {

  Optional<List<PlayerTeam>> findAllByPlayerId(UUID uuid);

  Optional<List<PlayerTeam>> findAllByTeamId(UUID uuid);
}
