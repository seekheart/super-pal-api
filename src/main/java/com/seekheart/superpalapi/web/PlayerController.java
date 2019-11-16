package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.PlayerRequest;
import com.seekheart.superpalapi.model.web.PlayerResponse;
import com.seekheart.superpalapi.service.PlayerService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/players")
public class PlayerController {

  private final PlayerService service;

  @GetMapping
  public List<PlayerResponse> getAllPlayers() {
    return service.findAll();
  }


  @GetMapping("/{id}")
  public PlayerResponse getOnePlayer(@PathVariable UUID id) {
    return service.findOne(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PlayerResponse createPlayer(@RequestBody PlayerRequest playerRequest) {
    return service.createPlayer(playerRequest);
  }

  @PutMapping("/{id}")
  public PlayerResponse editPlayer(@PathVariable UUID id, @RequestBody PlayerRequest player) {
    return service.editPlayer(id, player);
  }

  @DeleteMapping("/{id}")
  public void deleteOne(@PathVariable UUID id) {
    service.deletePlayer(id);
  }

  @PutMapping("/{id}/teams")
  public void editTeams(@PathVariable UUID id, @RequestBody PlayerRequest playerRequest) {
    service.editTeam(id, playerRequest);
  }

}
