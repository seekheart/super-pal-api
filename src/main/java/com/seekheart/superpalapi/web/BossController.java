package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.BossRequest;
import com.seekheart.superpalapi.model.web.BossResponse;
import com.seekheart.superpalapi.service.BossService;
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

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/bosses")
public class BossController {

  private BossService service;

  @GetMapping
  public List<BossResponse> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public BossResponse findOne(@PathVariable UUID id) {
    return service.findOne(id);
  }

  @GetMapping("/raids/{id}")
  public List<BossResponse> findByRaidId(@PathVariable UUID id) {
    return service.findByRaid(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public BossResponse addOne(@RequestBody BossRequest request) {
    return service.addBossToRaid(request);
  }

  @PutMapping("/{id}")
  public BossResponse editOne(@PathVariable UUID id, @RequestBody BossRequest request) {
    return service.editOne(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteOne(@PathVariable UUID id) {
    service.deleteOne(id);
  }

}
