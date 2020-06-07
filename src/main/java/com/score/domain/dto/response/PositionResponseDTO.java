package com.score.domain.dto.response;

import java.io.Serializable;
import java.util.Objects;

public class PositionResponseDTO implements Serializable {

  private Integer userId;
  private Integer score;
  private Integer position;

  public PositionResponseDTO() {}

  public PositionResponseDTO(Integer userId, Integer score, Integer position) {
    this.userId = userId;
    this.score = score;
    this.position = position;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PositionResponseDTO that = (PositionResponseDTO) o;
    return Objects.equals(userId, that.userId)
        && Objects.equals(score, that.score)
        && Objects.equals(position, that.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, score, position);
  }

  @Override
  public String toString() {
    return "PositionResponseDTO{"
        + "userId="
        + userId
        + ", score="
        + score
        + ", position="
        + position
        + '}';
  }
}
