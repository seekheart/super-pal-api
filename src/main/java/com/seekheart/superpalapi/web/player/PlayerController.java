package com.seekheart.superpalapi.web.player;

import com.seekheart.superpalapi.model.Player;
import com.seekheart.superpalapi.service.PlayerService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/players")
public class PlayerController {

  private final PlayerService service;

  public PlayerController(PlayerService service) {
    this.service = service;
  }

  @GetMapping
  public Iterable<Player> getAllPlayers() {
    return service.findAll();
  }

  @PostMapping
  public Player createPlayer(@RequestBody Player player) {
    return service.saveOne(player);
  }

  @GetMapping("/{id}")
  public Optional<Player> findPlayerById(@PathVariable Long id) {
    return service.findById(id);
  }

  @PutMapping("/{id}")
  public Player editPlayer(@PathVariable Long id, @RequestBody Player player) {
    log.info("Updating player id = {}", id);
    return service.updateOne(player);
  }
}
