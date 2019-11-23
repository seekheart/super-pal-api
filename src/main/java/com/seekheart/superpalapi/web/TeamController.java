package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.TeamRequest;
import com.seekheart.superpalapi.model.web.TeamResponse;
import com.seekheart.superpalapi.service.TeamService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {

  private TeamService teamService;

  @GetMapping
  public ResponseEntity<List<TeamResponse>> findAll() {
    return ResponseEntity.ok(teamService.findAll());
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity<TeamResponse> createOne(@RequestBody TeamRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createOne(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editOne(@PathVariable UUID id, @RequestBody TeamRequest request) {
    try {
      log.info("Performing PUT request for team id = {}", id);
      TeamResponse result = teamService.editOne(id, request);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteOne(@PathVariable UUID id) {
    Boolean result = teamService.deleteOne(id);
    if (result) {
      return ResponseEntity.ok("success");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
