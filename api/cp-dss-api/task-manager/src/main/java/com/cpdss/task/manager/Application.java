/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Bootstrap class for SpringBoot microservice */
@SpringBootApplication
public class Application {

  /**
   * main for spring-boot
   *
   * @param args - arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
