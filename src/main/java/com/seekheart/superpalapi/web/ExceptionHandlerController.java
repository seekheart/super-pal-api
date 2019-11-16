package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.error.LeagueNotFoundException;
import com.seekheart.superpalapi.model.error.PlayerNotFoundException;
import com.seekheart.superpalapi.model.error.TeamNotFoundException;
import com.seekheart.superpalapi.model.error.TeamNullException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler({PlayerNotFoundException.class, LeagueNotFoundException.class,
      TeamNotFoundException.class})
  public void springHandleNotFound(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.NOT_FOUND.value());
  }

  @ExceptionHandler({TeamNullException.class})
  public void springHandleNullRequest(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.BAD_REQUEST.value());
  }

}
