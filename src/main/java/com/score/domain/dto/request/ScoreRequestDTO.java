package com.score.domain.dto.request;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

public class ScoreRequestDTO implements Serializable {

  private final Integer userId;
  private final Integer score;

  public ScoreRequestDTO(Integer userId, Integer points) {
    Assert.notNull(userId, "userId must not be null");
    Assert.notNull(points, "points must not be null");
    this.userId = userId;
    this.score = points;
  }

  public Integer getUserId() {
    return userId;
  }

  public Integer getScore() {
    return score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ScoreRequestDTO that = (ScoreRequestDTO) o;
    return Objects.equals(userId, that.userId) && Objects.equals(score, that.score);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, score);
  }

  @Override
  public String toString() {
    return "ScoreRequestDTO{" + "userId=" + userId + ", score=" + score + '}';
  }
}
