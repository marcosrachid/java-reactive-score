package com.score.service;

import com.score.domain.dto.request.ScoreRequestDTO;
import com.score.domain.dto.response.PositionResponseDTO;
import com.score.domain.repository.User;
import com.score.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
public class UserService implements IUserService {

  private ApplicationEventPublisher publisher;
  private UserRepository userRepository;

  public UserService(ApplicationEventPublisher publisher, UserRepository userRepository) {
    this.publisher = publisher;
    this.userRepository = userRepository;
  }

  @Override
  public void score(ScoreRequestDTO scoreRequest) {}

  @Override
  public Mono<PositionResponseDTO> position(Integer userId) {
    return Mono.defer(
        () -> {
          Optional<User> user = userRepository.getUser(userId);
          if (!user.isPresent()) {
            return Mono.empty();
          }
          User u = user.get();
          OptionalInt position = userRepository.getPosition(userId);
          return Mono.just(
              new PositionResponseDTO(u.getUserId(), u.getScore(), position.getAsInt()));
        });
  }

  @Override
  public Flux<PositionResponseDTO> highscorelist(Integer page, Integer size) {
    return Flux.defer(
        () -> {
          List<User> users = userRepository.getUsers(page, size);
          return Flux.fromStream(
              IntStream.range(0, users.size())
                  .mapToObj(
                      i ->
                          new PositionResponseDTO(
                              users.get(i).getUserId(), users.get(i).getScore(), i)));
        });
  }
}
