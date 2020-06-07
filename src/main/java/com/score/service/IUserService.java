package com.score.service;

import com.score.domain.dto.request.ScoreRequestDTO;
import com.score.domain.dto.response.PositionResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {

  void score(ScoreRequestDTO scoreRequest);

  Mono<PositionResponseDTO> position(Integer userId);

  Flux<PositionResponseDTO> highscorelist(Integer page, Integer size);
}
