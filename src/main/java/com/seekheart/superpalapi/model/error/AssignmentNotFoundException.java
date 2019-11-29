package com.seekheart.superpalapi.model.error;

import java.util.UUID;

public class AssignmentNotFoundException extends RuntimeException {

  public AssignmentNotFoundException(UUID assignmentId) {
    super("Assignment id=" + assignmentId + " not found");
  }

}
