package com.seekheart.superpalapi.web;

import com.seekheart.superpalapi.model.error.BadRaidRequest;
import com.seekheart.superpalapi.model.error.BossNotFoundException;
import com.seekheart.superpalapi.model.error.RaidAlreadyActiveException;
import com.seekheart.superpalapi.model.error.RaidBossNotFoundException;
import com.seekheart.superpalapi.model.error.RaidNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
      BossNotFoundException.class,
      RaidNotFoundException.class,
      RaidBossNotFoundException.class
  })
  public void handleNotFound(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.NOT_FOUND.value());
  }

  @ExceptionHandler({
      RaidAlreadyActiveException.class,
      BadRaidRequest.class
  })
  public void handleBadRequest(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.BAD_REQUEST.value());
  }

}
