/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.vesselinfo.entity.PumpType;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselPumps;
import com.cpdss.vesselinfo.repository.PumpTypeRepository;
import com.cpdss.vesselinfo.repository.VesselPumpRepository;
import com.cpdss.vesselinfo.repository.VesselRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** Master service for Pump Operations */
@Slf4j
@Service
public class VesselPumpService {

  @Autowired VesselRepository vesselRepository;

  @Autowired VesselPumpRepository vesselPumpRepository;

  @Autowired PumpTypeRepository pumpTypeRepository;

  public VesselInfo.VesselPumpsResponse getVesselPumpsAndTypes(
      VesselInfo.VesselPumpsResponse.Builder builder, Long vesselId)
      throws GenericServiceException {
    Common.ResponseStatus.Builder builder1 = Common.ResponseStatus.newBuilder().setStatus("FAILED");
    Pageable defaultPage = PageRequest.of(0, 1000);
    Vessel vessel = vesselRepository.findByIdAndIsActive(vesselId, true);
    if (vessel != null) {
      Page<PumpType> pumpTypes = pumpTypeRepository.findAll(defaultPage);
      Page<VesselPumps> vesselPumps =
          vesselPumpRepository.findAllByVesselAndIsActiveTrue(vessel, defaultPage);
      this.buildPumpTypeGrpcBuilder(pumpTypes.toList(), builder);
      this.buildVesselPumpGrpcBuilder(vesselPumps.toList(), builder);
      builder1.setStatus("SUCCESS");
      builder.setResponseStatus(builder1);
      log.info(
          "Vessel pumps for vessel {}, Pump size {}, Type Size {}",
          vesselId,
          vesselPumps.getTotalElements(),
          pumpTypes.getTotalElements());
    }
    return builder.build();
  }

  private void buildPumpTypeGrpcBuilder(
      List<PumpType> list, VesselInfo.VesselPumpsResponse.Builder builder) {
    for (PumpType py : list) {
      VesselInfo.PumpType.Builder builder2 = VesselInfo.PumpType.newBuilder();
      builder2.setId(py.getId());
      builder2.setName(py.getName());
      builder.addPumpType(builder2);
    }
  }

  private void buildVesselPumpGrpcBuilder(
      List<VesselPumps> list, VesselInfo.VesselPumpsResponse.Builder builder) {
    for (VesselPumps vp : list) {
      VesselInfo.VesselPump.Builder builder2 = VesselInfo.VesselPump.newBuilder();
      builder2.setId(vp.getId());
      builder2.setVesselId(vp.getVessel().getId());
      builder2.setPumpTypeId(vp.getPumpType().getId());
      builder2.setPumpName(vp.getPumpName());
      builder2.setPumpCode(vp.getPumpCode());
      builder2.setPumpCapacity(vp.getCapacity().toString());
      builder.addVesselPump(builder2);
    }
  }
}
