package com.score.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

// Not using constraint validators due to ObjectMapper fails to map json between types and ignores
// some validations
public class ScoreRequestDTO implements Serializable {

  private final Integer userId;
  private final Integer points;

  @JsonCreator
  public ScoreRequestDTO(
      @JsonProperty(value = "userId") Integer userId,
      @JsonProperty(value = "points") Integer points) {
    this.userId = userId;
    this.points = points;
    Assert.notNull(this.userId, "userId must not be null");
    Assert.isTrue(this.userId > 0, "userId must be positive");
    Assert.notNull(this.points, "points must not be null");
    Assert.isTrue(this.points> 0, "points must be positive");
  }

  public Integer getUserId() {
    return userId;
  }

  public Integer getPoints() {
    return points;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ScoreRequestDTO that = (ScoreRequestDTO) o;
    return Objects.equals(userId, that.userId) && Objects.equals(points, that.points);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, points);
  }

  @Override
  public String toString() {
    return "ScoreRequestDTO{" + "userId=" + userId + ", points=" + points + '}';
  }
}
