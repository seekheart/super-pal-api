package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.PlayerResponse;
import com.seekheart.superpalapi.service.PlayerService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
