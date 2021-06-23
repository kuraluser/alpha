/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo;

import com.cpdss.vesselinfo.service.VesselPumpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired VesselPumpService vesselPumpService;
}
