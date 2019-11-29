package com.seekheart.superpalapi.model.error;

public class GenericBadRequest extends RuntimeException {

  public GenericBadRequest() {
    super("Bad Request was made!");
  }
}
