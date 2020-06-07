package com.score.domain.repository;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable, Comparable<User> {

  private Integer userId;
  // AtomicInteger is necessary due to its thread safe characteristics since it could be a scenario
  // with multiple concurrent requests to the same userId
  private AtomicInteger score = new AtomicInteger(0);

  public User() {}

  public User(Integer userId, Integer points) {
    this.userId = userId;
    this.score.addAndGet(points);
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getScore() {
    return score.get();
  }

  public void setScore(Integer score) {
    this.score = new AtomicInteger(score);
  }

  public void addScore(Integer score) {
    this.score.addAndGet(score);
  }

  @Override
  public int compareTo(User user) {
    return user.getScore().compareTo(this.getScore());
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
