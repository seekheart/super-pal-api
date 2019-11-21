package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.LeagueRequest;
import com.seekheart.superpalapi.model.web.LeagueResponse;
import com.seekheart.superpalapi.service.LeagueService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leagues")
@AllArgsConstructor
public class LeagueController {

  private LeagueService leagueService;

  @GetMapping
  public List<LeagueResponse> findAll() {
    return leagueService.findAll();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public LeagueResponse createOne(@RequestBody LeagueRequest request) {
    return leagueService.createLeague(request);
  }

  @PutMapping("/{id}")
  public LeagueResponse editOne(@PathVariable UUID id, @RequestBody LeagueRequest request) {
    return leagueService.editLeague(id, request);
  }
}
