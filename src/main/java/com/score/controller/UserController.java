package com.score.controller;

import com.score.domain.dto.ErrorDTO;
import com.score.domain.dto.request.ScoreRequestDTO;
import com.score.domain.dto.response.PositionResponseDTO;
import com.score.domain.dto.swagger.ListPositionResponseSwaggerDTO;
import com.score.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

  private IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

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
    userService.score(scoreRequest);
    return ResponseEntity.ok().build();
  }

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
    Mono<PositionResponseDTO> e = userService.position(userId);
    HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    return ResponseEntity.status(status).body(e);
  }

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
      @RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size) {
    return ResponseEntity.ok(userService.highscorelist(page, size));
  }
}
