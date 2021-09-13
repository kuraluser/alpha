/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;
import java.util.*;
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

  @Autowired TankTypeRepository tankTypeRepository;

  @Autowired VesselManifoldRepository vesselManifoldRepository;

  @Autowired VesselBottomLineRepository vesselBottomLineRepository;

  @Autowired VesselValveSequenceRepository vesselValveSequenceRepository;

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
      Page<TankType> tankTypes = tankTypeRepository.findAll(defaultPage);
      Page<VesselManifold> vesselManifolds = vesselManifoldRepository.findAll(defaultPage);
      Page<VesselBottomLine> vesselBottomLines = vesselBottomLineRepository.findAll(defaultPage);
      this.buildPumpTypeGrpcBuilder(pumpTypes.toList(), builder);
      this.buildVesselPumpGrpcBuilder(vesselPumps.toList(), builder);
      this.buildVesselDetails(vessel, builder);
      this.buildVesselTankDetails(tankTypes.toList(), builder);
      this.buildVesselManifolds(vesselManifolds.toList(), builder);
      this.buildVesselBottomLine(vesselBottomLines.toList(), builder);
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

  private void buildVesselBottomLine(
      List<VesselBottomLine> toList, VesselInfo.VesselPumpsResponse.Builder builder) {
    for (VesselBottomLine bl : toList) {
      VesselInfo.VesselComponent.Builder builder1 = VesselInfo.VesselComponent.newBuilder();
      Optional.ofNullable(bl.getId()).ifPresent(builder1::setId);
      Optional.ofNullable(bl.getBottomLineName()).ifPresent(builder1::setComponentName);
      Optional.ofNullable(bl.getBottomLineCode()).ifPresent(builder1::setComponentCode);
      Optional.ofNullable(bl.getVesselXid()).ifPresent(builder1::setVesselId);
      builder1.setComponentType(-1L);
      builder.addVesselBottomLine(builder1.build());
    }
  }

  private void buildVesselManifolds(
      List<VesselManifold> toList, VesselInfo.VesselPumpsResponse.Builder builder) {
    for (VesselManifold vm : toList) {
      VesselInfo.VesselComponent.Builder builder1 = VesselInfo.VesselComponent.newBuilder();
      Optional.ofNullable(vm.getId()).ifPresent(builder1::setId);
      Optional.ofNullable(vm.getManifoldName()).ifPresent(builder1::setComponentName);
      Optional.ofNullable(vm.getManifoldCode()).ifPresent(builder1::setComponentCode);
      Optional.ofNullable(vm.getVesselXid()).ifPresent(builder1::setVesselId);
      Optional.ofNullable(vm.getTankType().getId()).ifPresent(builder1::setComponentType);
      if (vm.getTankType() != null) {
        Optional.ofNullable(vm.getTankType().getId()).ifPresent(builder1::setTankTypeId);
        Optional.ofNullable(vm.getTankType().getTypeName()).ifPresent(builder1::setTankTypeName);
      }
      builder.addVesselManifold(builder1.build());
    }
  }

  private void buildVesselTankDetails(
      List<TankType> tankTypes, VesselInfo.VesselPumpsResponse.Builder builder) {
    for (TankType ty : tankTypes) {
      VesselInfo.TankType.Builder builder1 = VesselInfo.TankType.newBuilder();
      Optional.ofNullable(ty.getId()).ifPresent(builder1::setId);
      Optional.ofNullable(ty.getTypeName()).ifPresent(builder1::setTypeName);
      builder.addTankType(builder1.build());
    }
  }

  private void buildVesselDetails(Vessel vessel, VesselInfo.VesselPumpsResponse.Builder builder) {
    VesselInfo.VesselDetail.Builder builder1 = VesselInfo.VesselDetail.newBuilder();
    builder1.setId(vessel.getId());
    builder1.setHomogeneousLoadingRate(vessel.getHomogeneousLoadingRate().toString());
    builder1.setWingTankLoadingRate(vessel.getWingTankLoadingRate().toString());
    builder1.setCenterTankLoadingRate(vessel.getCenterTankLoadingRate().toString());
    builder1.setName(vessel.getName());
    builder.setVesselDetails(builder1.build());
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

  public List<VesselInfo.VesselValveSequence> buildVesselValveSeqMessage(
      List<VesselValveSequence> list) {
    List<VesselInfo.VesselValveSequence> sequenceList = new ArrayList<>();
    for (VesselValveSequence vvs : list) {
      VesselInfo.VesselValveSequence.Builder builder = VesselInfo.VesselValveSequence.newBuilder();
      try {
        Optional.ofNullable(vvs.getId()).ifPresent(builder::setId);
        Optional.ofNullable(vvs.getIsCommonValve()).ifPresent(builder::setIsCommonValve);
        Optional.ofNullable(vvs.getIsShut()).ifPresent(builder::setIsShut);
        Optional.ofNullable(vvs.getPipelineId()).ifPresent(builder::setPipelineId);
        Optional.ofNullable(vvs.getPipelineColor()).ifPresent(builder::setPipelineColor);
        Optional.ofNullable(vvs.getPipelineName()).ifPresent(builder::setPipelineName);
        Optional.ofNullable(vvs.getPipelineType()).ifPresent(builder::setPipelineType);
        Optional.ofNullable(vvs.getPumpCode()).ifPresent(builder::setPumpCode);
        Optional.ofNullable(vvs.getPumpName()).ifPresent(builder::setPumpName);
        Optional.ofNullable(vvs.getPumpType()).ifPresent(builder::setPumpType);
        Optional.ofNullable(vvs.getSequenceNumber())
            .ifPresent(v -> builder.setSequenceNumber(v.toString()));
        Optional.ofNullable(vvs.getSequenceOperationId())
            .ifPresent(builder::setSequenceOperationId);
        Optional.ofNullable(vvs.getSequenceOperationName())
            .ifPresent(builder::setSequenceOperationName);
        Optional.ofNullable(vvs.getSequenceTypeId()).ifPresent(builder::setSequenceTypeId);
        Optional.ofNullable(vvs.getSequenceTypeName()).ifPresent(builder::setSequenceTypeName);
        Optional.ofNullable(vvs.getSequenceVesselMappingId())
            .ifPresent(builder::setSequenceVesselMappingId);
        Optional.ofNullable(vvs.getTankShortName()).ifPresent(builder::setTankShortName);
        Optional.ofNullable(vvs.getStageNumber()).ifPresent(builder::setStageNumber);
        Optional.ofNullable(vvs.getValveCategory()).ifPresent(builder::setValveCategory);
        Optional.ofNullable(vvs.getValveCategoryId()).ifPresent(builder::setValveCategoryId);
        Optional.ofNullable(vvs.getValveId()).ifPresent(builder::setValveId);
        Optional.ofNullable(vvs.getValveNumber()).ifPresent(builder::setValveNumber);
        Optional.ofNullable(vvs.getValveSide()).ifPresent(builder::setValveSide);
        Optional.ofNullable(vvs.getValveTypeId()).ifPresent(builder::setValveTypeId);
        Optional.ofNullable(vvs.getValveTypeName()).ifPresent(builder::setValveTypeName);
        Optional.ofNullable(vvs.getVesselName()).ifPresent(builder::setVesselName);
        Optional.ofNullable(vvs.getVesselTankXid()).ifPresent(builder::setVesselTankXid);
        Optional.ofNullable(vvs.getVesselXid()).ifPresent(builder::setVesselXid);
      } catch (Exception e) {
        log.error("Failed to build message for vessel valve sequence");
        e.printStackTrace();
      }
      sequenceList.add(builder.build());
    }
    return sequenceList;
  }

  public List<VesselInfo.VesselValveEducationProcess> buildVesselValveEducator(
      List<VesselValveEducationProcess> data) {
    List<VesselInfo.VesselValveEducationProcess> list = new ArrayList<>();
    for (VesselValveEducationProcess vvEp : data) {
      try {
        VesselInfo.VesselValveEducationProcess.Builder builder =
            VesselInfo.VesselValveEducationProcess.newBuilder();
        Optional.ofNullable(vvEp.getId()).ifPresent(builder::setId);
        Optional.ofNullable(vvEp.getEductionProcessMasterId())
            .ifPresent(builder::setEductionProcessMasterId);
        Optional.ofNullable(vvEp.getEductorId()).ifPresent(builder::setEductorId);
        Optional.ofNullable(vvEp.getEductorName()).ifPresent(builder::setEductorName);
        Optional.ofNullable(vvEp.getSequenceNumber()).ifPresent(builder::setSequenceNumber);
        Optional.ofNullable(vvEp.getStepName()).ifPresent(builder::setStepName);
        Optional.ofNullable(vvEp.getValveNumber()).ifPresent(builder::setValveNumber);
        Optional.ofNullable(vvEp.getStageNumber()).ifPresent(builder::setStageNumber);
        Optional.ofNullable(vvEp.getValveId()).ifPresent(builder::setValveId);
        Optional.ofNullable(vvEp.getStageName()).ifPresent(builder::setStageName);
        list.add(builder.build());
      } catch (Exception e) {
        log.error("Failed to build message for vessel valve educator");
        e.printStackTrace();
      }
    }
    return list;
  }
}
