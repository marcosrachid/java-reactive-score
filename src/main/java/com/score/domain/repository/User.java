package com.score.domain.repository;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable, Comparable<User> {

  private Integer userId;
  private Integer score = 0;

  public User() {}

  public User(Integer userId, Integer score) {
    this.userId = userId;
    this.score = score;
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

  public void addScore(Integer score) {
    this.score = this.score + score;
  }

  @Override
  public int compareTo(User user) {
    return this.score.compareTo(user.score);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, score);
  }

  @Override
  public String toString() {
    return "User{" + "userId=" + userId + ", score=" + score + '}';
  }
}
