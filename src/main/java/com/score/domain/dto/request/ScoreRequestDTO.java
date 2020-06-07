package com.score.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.score.utils.Messages;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

// Not using constraint validators due to ObjectMapper fails to map json between types and ignores
// some validations
public class ScoreRequestDTO implements Serializable {

  private Integer userId;
  private Integer points;

  @JsonCreator
  public ScoreRequestDTO(
      @JsonProperty(value = "userId") Integer userId,
      @JsonProperty(value = "points") Integer points) {
    this.userId = userId;
    this.points = points;
    Assert.notNull(this.userId, Messages.USER_ID_NOT_NULL);
    Assert.isTrue(this.userId > 0, Messages.USER_ID_POSITIVE);
    Assert.notNull(this.points, Messages.POINTS_NOT_NULL);
    Assert.isTrue(this.points > 0, Messages.POINTS_POSITIVE);
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

  public static class ScoreRequestDTOBuilder {

    ScoreRequestDTO scoreRequestDTO = null;

    public ScoreRequestDTOBuilder() {
      this.scoreRequestDTO = new ScoreRequestDTO(1, 1);
    }

    public ScoreRequestDTOBuilder withUserId(Integer userId) {
      this.scoreRequestDTO.userId = userId;
      return this;
    }

    public ScoreRequestDTOBuilder withPoints(Integer points) {
      this.scoreRequestDTO.points = points;
      return this;
    }

    public ScoreRequestDTO build() {
      return scoreRequestDTO;
    }
  }
}
