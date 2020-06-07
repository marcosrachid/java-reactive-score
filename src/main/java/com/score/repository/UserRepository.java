package com.score.repository;

import com.score.domain.repository.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class UserRepository implements IUserRepository {

  private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

  private CopyOnWriteArrayList<User> users;

  public UserRepository(CopyOnWriteArrayList<User> users) {
    this.users = users;
  }

  @Override
  public void score(Integer userId, Integer score) {
    LOG.trace("score: {}, {}", userId, score);
    Optional<User> optionalUser =
        users.stream().filter(u -> u.getUserId().equals(userId)).findAny();
    if (optionalUser.isPresent()) {
      optionalUser.get().addScore(score);
      LOG.trace("user exists, summed score: {}", optionalUser.get());
    } else {
      User user = new User(userId, score);
      users.add(user);
      LOG.trace("user does not exists, summed score: {}", user);
    }
    Collections.sort(users);
  }

  @Override
  public Optional<User> getUser(Integer userId) {
    LOG.trace("getUser: {}", userId);
    return users.stream().filter(u -> u.getUserId().equals(userId)).findAny();
  }

  @Override
  public List<User> getUsers(Integer page, Integer size) {
    LOG.trace("getUsers: {}, {}", page, size);
    Integer startIndex = (page - 1) * size;
    LOG.trace("startIndex: {}", startIndex);
    Integer endIndex = page * size;
    LOG.trace("endIndex: {}", endIndex);
    return users.stream()
        .skip(startIndex)
        .limit(endIndex - startIndex)
        .collect(Collectors.toList());
  }

  @Override
  public OptionalInt getPosition(Integer userId) {
    LOG.trace("getPosition: {}", userId);
    return IntStream.range(0, users.size())
        .filter(i -> users.get(i).getUserId().equals(userId))
        .findAny();
  }
}
