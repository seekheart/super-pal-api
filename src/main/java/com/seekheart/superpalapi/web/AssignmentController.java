package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.web.AssignmentRequest;
import com.seekheart.superpalapi.model.web.AssignmentResponse;
import com.seekheart.superpalapi.service.AssignmentService;
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
@RequestMapping("/assignments")
@Slf4j
@AllArgsConstructor
public class AssignmentController {

  private AssignmentService service;

  @GetMapping
  public List<AssignmentResponse> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public AssignmentResponse findOne(@PathVariable UUID id) {
    return service.findById(id);
  }

  @GetMapping("/players/{playerId}")
  public List<AssignmentResponse> findPlayerAssignments(@PathVariable UUID playerId) {
    return service.findByPlayer(playerId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public AssignmentResponse saveOne(@RequestBody AssignmentRequest request) {
    return service.saveOne(request);
  }

  @PutMapping("/{id}")
  public AssignmentResponse editOne(@PathVariable UUID id, @RequestBody AssignmentRequest request) {
    return service.editOne(id, request);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteOne(@PathVariable UUID id) {
    service.deleteOne(id);
  }
}
