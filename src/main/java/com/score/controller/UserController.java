package com.score.controller;

import com.score.domain.dto.ErrorDTO;
import com.score.domain.dto.request.PaginatorRequestDTO;
import com.score.domain.dto.request.ScoreRequestDTO;
import com.score.domain.dto.response.PositionResponseDTO;
import com.score.domain.dto.swagger.ListPositionResponseSwaggerDTO;
import com.score.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Web flux controller for non blocking event-driven requests */
@RestController
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  /**
   * @param scoreRequest
   * @return
   */
  @ApiOperation(value = "Operation add points to a specific user")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "User points registered successfully",
            response = Void.class),
        @ApiResponse(
            code = 400,
            message = "This is a bad request, please stick to the API description",
            response = ErrorDTO.class),
        @ApiResponse(
            code = 500,
            message = "An internal server error occured, notify your administrator",
            response = ErrorDTO.class)
      })
  @RequestMapping(
      value = {"/score"},
      method = RequestMethod.POST)
  public ResponseEntity<Void> score(@RequestBody ScoreRequestDTO scoreRequest) {
    LOG.debug("REST request to score: {}", scoreRequest);
    userService.score(scoreRequest);
    return ResponseEntity.ok().build();
  }

  /**
   * @param userId
   * @return
   */
  @ApiOperation(value = "Operation to get a specific user's position")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "User returned successfully",
            response = PositionResponseDTO.class),
        @ApiResponse(
            code = 404,
            message = "This is a bad request, please stick to the API description",
            response = Void.class),
        @ApiResponse(
            code = 500,
            message = "An internal server error occured, notify your administrator",
            response = ErrorDTO.class)
      })
  @RequestMapping(
      value = {"/{userId}/position"},
      method = RequestMethod.GET)
  public ResponseEntity<Mono<PositionResponseDTO>> position(
      @PathVariable("userId") Integer userId) {
    LOG.debug("REST request to get position: {}", userId);
    return ResponseEntity.ok(userService.position(userId));
  }

  /**
   * @param paginatorRequestDTO
   * @return
   */
  @ApiOperation(value = "Operation to list in order of position all registered users")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Users listed successfully",
            response = ListPositionResponseSwaggerDTO.class),
        @ApiResponse(
            code = 400,
            message = "This is a bad request, please stick to the API description",
            response = ErrorDTO.class),
        @ApiResponse(
            code = 500,
            message = "An internal server error occured, notify your administrator",
            response = ErrorDTO.class)
      })
  @RequestMapping(
      value = {"/highscorelist"},
      method = RequestMethod.GET)
  public ResponseEntity<Flux<PositionResponseDTO>> highscorelist(
      PaginatorRequestDTO paginatorRequestDTO) {
    LOG.debug("REST request to list high score: {}", paginatorRequestDTO);
    return ResponseEntity.ok(userService.highscorelist(paginatorRequestDTO));
  }
}
