package com.score.repository;

import com.score.domain.repository.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class UserRepository implements IUserRepository {

  private ConcurrentSkipListSet<User> users;

  public UserRepository(ConcurrentSkipListSet<User> users) {
    this.users = users;
  }

  @Override
  public void score(Integer userId, Integer score) {
    Optional<User> user = users.stream().filter(u -> u.getUserId().equals(userId)).findAny();
    if (user.isPresent()) {
      user.get().addScore(score);
      return;
    }
    users.add(new User(userId, score));
  }

  @Override
  public Optional<User> getUser(Integer userId) {
    return users.stream().filter(u -> u.getUserId().equals(userId)).findAny();
  }

  @Override
  public List<User> getUsers(Integer page, Integer size) {
    Integer startIndex = (page - 1) * size;
    Integer endIndex = page * size;
    return users.stream()
        .skip(startIndex)
        .limit(endIndex - startIndex + 1)
        .collect(Collectors.toList());
  }

  @Override
  public OptionalInt getPosition(Integer userId) {
    List<User> list = new ArrayList<>(users);
    return IntStream.range(0, list.size())
        .filter(i -> list.get(i).getUserId().equals(userId))
        .findAny();
  }
}
