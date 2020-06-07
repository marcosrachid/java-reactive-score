package com.score.controller.exception.handler;

import com.score.domain.dto.ErrorDTO;
import com.score.utils.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice(basePackages = "com.score.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
  private static final String WEB_ERROR = "Web Error: {}";

  @ExceptionHandler(ServerWebInputException.class)
  ResponseEntity<Object> handleResourceNotFound(ServerWebInputException ex) {
    LOG.debug(WEB_ERROR, ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                String.format(ex.getMostSpecificCause().getLocalizedMessage())));
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Object> handleResourceNotFound(Exception ex) {
    LOG.debug(WEB_ERROR, ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR));
  }
}
