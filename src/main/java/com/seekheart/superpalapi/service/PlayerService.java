package com.seekheart.superpalapi.service;

import com.seekheart.superpalapi.model.Player;
import com.seekheart.superpalapi.repository.PlayerRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
  private PlayerRepository repository;

  public PlayerService(PlayerRepository repository) {
    this.repository = repository;
  }

  public Iterable<Player> findAll() {
    return this.repository.findAll();
  }

  public Player saveOne(Player player) {
    return this.repository.save(player);
  }

  public Optional<Player> findById(Long id) {
    return repository.findById(id);
  }

  public Player updateOne(Player player) {
    return repository.save
        (player);
  }
}
