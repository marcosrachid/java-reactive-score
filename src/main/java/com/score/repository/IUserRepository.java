package com.score.repository;

import com.score.domain.repository.User;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface IUserRepository {

  void score(Integer userId, Integer points);

  Optional<User> getUser(Integer userId);

  List<User> getUsers(Integer page, Integer size);

  OptionalInt getPosition(Integer userId);
}
