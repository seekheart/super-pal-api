package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.Player;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, UUID> {

  Optional<Player> findByDiscordId(String discordId);
}
