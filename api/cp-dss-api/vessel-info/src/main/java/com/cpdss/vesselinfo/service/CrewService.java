/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.generated.VesselInfo;
import com.cpdss.vesselinfo.repository.CrewRankRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Crew Service for crew related services Author: Athul c.p */
@Transactional
@Log4j2
@Service
public class CrewService {

  @Autowired CrewRankRepository crewRankRepository;

  /**
   * To get all crew rank
   *
   * @param builder
   */
  public void getAllCrewRank(VesselInfo.CrewReply.Builder builder) {
    List<Object[]> list = crewRankRepository.findActiveCrewRank();
    if (list == null) return;
    for (Object[] obj : list) {
      VesselInfo.CrewRank.Builder crewRank = VesselInfo.CrewRank.newBuilder();
      crewRank.setId(Long.valueOf(obj[0].toString()));
      String name = (String) obj[1];
      if (name != null) crewRank.setCrewName(name);
      String shortName = (String) obj[2];
      if (shortName != null) crewRank.setRankShortName(shortName);
      builder.addCrewRanks(crewRank);
    }
  }
}
