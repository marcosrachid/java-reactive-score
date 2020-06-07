package com.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
public class ScoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScoreApplication.class, args);
  }
}
