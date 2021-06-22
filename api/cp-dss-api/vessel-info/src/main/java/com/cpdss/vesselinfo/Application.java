/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo;

import com.cpdss.vesselinfo.entity.VesselValveSequence;
import com.cpdss.vesselinfo.repository.VesselValveSequenceRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired VesselValveSequenceRepository vesselValveSequenceRepository;

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext context) {
    return args -> {
      List<VesselValveSequence> ls = vesselValveSequenceRepository.findAll();
      log.info("list size {}", ls.size());
      ls.forEach(System.out::println);
    };
  }
}
