package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.RaidRequest;
import com.seekheart.superpalapi.model.web.RaidResponse;
import com.seekheart.superpalapi.service.RaidService;
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
@RequestMapping("/raids")
@Slf4j
@AllArgsConstructor
public class RaidController {

  private RaidService raidService;

  @GetMapping
  public List<RaidResponse> findAll() {
    return raidService.findAll();
  }

  @GetMapping("/{id}")
  public RaidResponse findOne(@PathVariable UUID id) {
    return raidService.findOne(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public RaidResponse createOne(@RequestBody RaidRequest request) {
    return raidService.CreateOne(request);
  }

  @PutMapping("/{id}")
  public RaidResponse editOne(@PathVariable UUID id, @RequestBody RaidRequest request) {
    return raidService.editOne(id, request);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteOne(@PathVariable UUID id) {
    raidService.deleteOne(id);
  }

}
