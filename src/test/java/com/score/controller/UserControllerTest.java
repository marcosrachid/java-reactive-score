package com.score.controller;

import com.score.domain.dto.ErrorDTO;
import com.score.domain.dto.request.ScoreRequestDTO;
import com.score.domain.dto.response.PositionResponseDTO;
import com.score.domain.repository.User;
import com.score.repository.UserRepository;
import com.score.service.UserService;
import com.score.utils.Messages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@Import(UserService.class)
public class UserControllerTest {

  @MockBean private UserRepository userRepository;

  @Autowired private WebTestClient webClient;

  @Test
  public void testScoreSuccessfully() {
    Integer userdIdInput = 123;
    Integer pointsInput = 10;

    doNothing().when(userRepository).score(userdIdInput, pointsInput);

    webClient
        .post()
        .uri("/score")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(new ScoreRequestDTO(userdIdInput, pointsInput))
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  public void testScoreUserIdNull() {
    Integer userdIdInput = null;
    Integer pointsInput = 10;

    doNothing().when(userRepository).score(userdIdInput, pointsInput);

    webClient
        .post()
        .uri("/score")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(
            new ScoreRequestDTO.ScoreRequestDTOBuilder()
                .withUserId(userdIdInput)
                .withPoints(pointsInput)
                .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.USER_ID_NOT_NULL));
  }

  @Test
  public void testScoreUserIdZero() {
    Integer userdIdInput = 0;
    Integer pointsInput = 10;

    doNothing().when(userRepository).score(userdIdInput, pointsInput);

    webClient
        .post()
        .uri("/score")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(
            new ScoreRequestDTO.ScoreRequestDTOBuilder()
                .withUserId(userdIdInput)
                .withPoints(pointsInput)
                .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.USER_ID_POSITIVE));
  }

  @Test
  public void testScoreUserIdNegative() {
    Integer userdIdInput = -1;
    Integer pointsInput = 10;

    doNothing().when(userRepository).score(userdIdInput, pointsInput);

    webClient
        .post()
        .uri("/score")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(
            new ScoreRequestDTO.ScoreRequestDTOBuilder()
                .withUserId(userdIdInput)
                .withPoints(pointsInput)
                .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.USER_ID_POSITIVE));
  }

  @Test
  public void testScorePointsNull() {
    Integer userdIdInput = 1;
    Integer pointsInput = null;

    doNothing().when(userRepository).score(userdIdInput, pointsInput);

    webClient
        .post()
        .uri("/score")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(
            new ScoreRequestDTO.ScoreRequestDTOBuilder()
                .withUserId(userdIdInput)
                .withPoints(pointsInput)
                .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.POINTS_NOT_NULL));
  }

  @Test
  public void testScorePointsZero() {
    Integer userdIdInput = 1;
    Integer pointsInput = 0;

    doNothing().when(userRepository).score(userdIdInput, pointsInput);

    webClient
        .post()
        .uri("/score")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(
            new ScoreRequestDTO.ScoreRequestDTOBuilder()
                .withUserId(userdIdInput)
                .withPoints(pointsInput)
                .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.POINTS_POSITIVE));
  }

  @Test
  public void testScorePointsNegative() {
    Integer userdIdInput = 1;
    Integer pointsInput = -10;

    doNothing().when(userRepository).score(userdIdInput, pointsInput);

    webClient
        .post()
        .uri("/score")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(
            new ScoreRequestDTO.ScoreRequestDTOBuilder()
                .withUserId(userdIdInput)
                .withPoints(pointsInput)
                .build())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.POINTS_POSITIVE));
  }

  @Test
  public void testPositionFoundSuccessfully() {
    Integer userIdInput = 1;

    when(userRepository.getUser(userIdInput)).thenReturn(Optional.of(new User(1, 10)));
    when(userRepository.getPosition(userIdInput)).thenReturn(OptionalInt.of(0));

    webClient
        .get()
        .uri(String.format("/%s/position", userIdInput))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(PositionResponseDTO.class)
        .value(p -> p.getPosition(), equalTo(1))
        .value(p -> p.getUserId(), equalTo(1))
        .value(p -> p.getScore(), equalTo(10));
  }

  @Test
  public void testPositionNotFoundSuccessfully() {
    Integer userIdInput = 1;

    when(userRepository.getUser(userIdInput)).thenReturn(Optional.ofNullable(null));

    webClient
        .get()
        .uri(String.format("/%s/position", userIdInput))
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  public void testHighscoreListEmpty() {
    Integer page = 1;
    Integer size = 10;

    when(userRepository.getUsers(page, size)).thenReturn(Collections.EMPTY_LIST);

    webClient
        .get()
        .uri("/highscorelist")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(PositionResponseDTO.class)
        .hasSize(0);
  }

  @Test
  public void testHighscoreListNotEmpty() {
    Integer page = 1;
    Integer size = 10;

    when(userRepository.getUsers(page, size))
        .thenReturn(
            Stream.of(new User(2, 50), new User(3, 25), new User(1, 20))
                .collect(Collectors.toList()));

    webClient
        .get()
        .uri("/highscorelist")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(PositionResponseDTO.class)
        .hasSize(3)
        .value(l -> l.get(0).getPosition(), equalTo(1))
        .value(l -> l.get(0).getUserId(), equalTo(2))
        .value(l -> l.get(1).getPosition(), equalTo(2))
        .value(l -> l.get(1).getUserId(), equalTo(3))
        .value(l -> l.get(2).getPosition(), equalTo(3))
        .value(l -> l.get(2).getUserId(), equalTo(1));
  }

  @Test
  public void testHighscoreListWithPageAndSize() {
    Integer page = 15;
    Integer size = 100;

    when(userRepository.getUsers(page, size)).thenReturn(Collections.EMPTY_LIST);

    webClient
        .get()
        .uri("/highscorelist?page=" + page + "&size=" + size)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(PositionResponseDTO.class)
        .hasSize(0);
  }

  @Test
  public void testHighscoreListWithPageNegative() {
    Integer page = -15;
    Integer size = 100;

    when(userRepository.getUsers(page, size)).thenReturn(Collections.EMPTY_LIST);

    webClient
        .get()
        .uri("/highscorelist?page=" + page + "&size=" + size)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.PAGE_POSITIVE));
  }

  @Test
  public void testHighscoreListWithSizeNegative() {
    Integer page = 15;
    Integer size = -100;

    when(userRepository.getUsers(page, size)).thenReturn(Collections.EMPTY_LIST);

    webClient
        .get()
        .uri("/highscorelist?page=" + page + "&size=" + size)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.SIZE_POSITIVE));
  }

  @Test
  public void testHighscoreListWithSizeOver20000() {
    Integer page = 15;
    Integer size = 30_000;

    when(userRepository.getUsers(page, size)).thenReturn(Collections.EMPTY_LIST);

    webClient
        .get()
        .uri("/highscorelist?page=" + page + "&size=" + size)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(ErrorDTO.class)
        .value(error -> error.getMessage(), equalTo(Messages.SIZE_LESSER_20000));
  }
}
