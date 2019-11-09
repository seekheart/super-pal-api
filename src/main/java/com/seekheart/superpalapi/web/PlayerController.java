package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.PlayerRequest;
import com.seekheart.superpalapi.model.web.PlayerResponse;
import com.seekheart.superpalapi.service.PlayerService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/players")
public class PlayerController {

  private final PlayerService service;

  @GetMapping
  public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
    return ResponseEntity.ok(service.findAll());
  }


  @GetMapping("/{id}")
  public ResponseEntity<PlayerResponse> getOnePlayer(@PathVariable UUID id) {
    try {
      return ResponseEntity.ok(service.findOne(id));
    } catch (Exception e) {
      log.error("Error handling request for player id = {}!", id);
      log.error(e.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<PlayerResponse> createPlayer(@RequestBody PlayerRequest playerRequest) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(service.createPlayer(playerRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PlayerResponse> editPlayer(@PathVariable UUID id,
      @RequestBody PlayerRequest player) {
    try {
      service.editPlayer(player);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteOne(@PathVariable UUID id) {
    Boolean result;
    try {
      result = service.deletePlayer(id);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.badRequest().build();
    }

    return result ? ResponseEntity.ok().build() :
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

}
