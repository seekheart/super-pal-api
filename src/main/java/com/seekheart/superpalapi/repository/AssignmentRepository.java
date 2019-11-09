package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.Assignment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, UUID> {

  List<Assignment> findAllByPlayerId(UUID id);
}
