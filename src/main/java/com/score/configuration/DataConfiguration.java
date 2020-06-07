package com.score.configuration;

import com.score.domain.repository.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class DataConfiguration {

  /**
   * CopyOnWriteArrayList is necessary due to its thread safe characteristics since its reactive
   * concurrency example and List because the index is necessary for ordering positions though it
   * can have some memory overusing since CopyOnWriteArrayList creates a copy to the heap on every
   * add. I couldn't use a Concurrent Set since comparables with the same score would mean for the
   * algorithm the same data and a Concurrent Map would need a Object Key with the score to order
   * and it would be a waste of feature for the same purpose as List
   *
   * @return
   */
  @Bean
  public CopyOnWriteArrayList<User> data() {
    return new CopyOnWriteArrayList<>();
  }
}
