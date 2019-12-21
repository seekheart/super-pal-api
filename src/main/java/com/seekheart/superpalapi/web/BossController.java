package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.BossRequest;
import com.seekheart.superpalapi.model.web.BossResponse;
import com.seekheart.superpalapi.service.BossService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bosses")
@Slf4j
@AllArgsConstructor
public class BossController {

  private BossService service;

  @GetMapping("/raids/{id}")
  public List<BossResponse> findBossById(@PathVariable UUID id) {
    return service.findBossesByRaidId(id);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void editBossHealth(@PathVariable UUID id, @RequestBody BossRequest request) {
    service.editBoss(id, request);
  }
}
