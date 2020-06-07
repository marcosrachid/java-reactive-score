package com.score.configuration;

import com.score.domain.repository.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentSkipListSet;

@Configuration
public class DataConfiguration {

  @Bean
  public ConcurrentSkipListSet<User> data() {
    return new ConcurrentSkipListSet<>();
  }
}
