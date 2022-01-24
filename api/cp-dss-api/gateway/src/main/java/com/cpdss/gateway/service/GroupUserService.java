/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.gateway.repository.GroupUserRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class GroupUserService {
  @Autowired private GroupUserRepository groupUserRepository;

  public List<Long> getVesselIds(Long userId) {
    return groupUserRepository.getAllVesselXId(userId);
  }
}
