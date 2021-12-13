package com.cpdss.vesselinfo.service;

import com.cpdss.common.generated.VesselInfo;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselCowParameters;
import com.cpdss.vesselinfo.repository.VesselCowParameterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VesselCowService {

    @Autowired
    VesselCowParameterRepository vesselCowParameterRepository;

    /**
     * Get From Vessel Cow Parameters Table and build data
     *
     * @param replyBuilder Vessel Details Algo RPC Response Builder
     * @param vessel       Vessel Entity Object
     */
    public void buildVesselCowParameters(VesselInfo.VesselAlgoReply.Builder replyBuilder, Vessel vessel) {
        List<VesselCowParameters> vcp = this.vesselCowParameterRepository.findAllByVesselIdAndIsActiveTrue(vessel.getId());
        if (!vcp.isEmpty()) {
            for (VesselCowParameters var1 : vcp) {
                VesselInfo.VesselCowParameters.Builder builder = VesselInfo.VesselCowParameters.newBuilder();
                Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
                Optional.ofNullable(var1.getVesselId()).ifPresent(builder::setVesselId);

                Optional.ofNullable(var1.getTopCowMinDuration()).ifPresent(value -> builder.setTopCowMinDuration(value.toString()));
                Optional.ofNullable(var1.getTopCowMaxDuration()).ifPresent(value -> builder.setTopCowMaxDuration(value.toString()));

                Optional.ofNullable(var1.getBottomCowMaxDuration()).ifPresent(value -> builder.setBottomCowMaxDuration(value.toString()));
                Optional.ofNullable(var1.getBottomCowMinDuration()).ifPresent(value -> builder.setBottomCowMinDuration(value.toString()));

                Optional.ofNullable(var1.getFullCowMaxDuration()).ifPresent(value -> builder.setFullCowMaxDuration(value.toString()));
                Optional.ofNullable(var1.getFullCowMinDuration()).ifPresent(value -> builder.setFullCowMinDuration(value.toString()));

                Optional.ofNullable(var1.getTopWashMinAngle()).ifPresent(value -> builder.setTopWashMinAngle(value.toString()));
                Optional.ofNullable(var1.getTopWashMaxAngle()).ifPresent(value -> builder.setTopWashMaxAngle(value.toString()));

                Optional.ofNullable(var1.getBottomWashMaxAngle()).ifPresent(value -> builder.setBottomWashMaxAngle(value.toString()));
                Optional.ofNullable(var1.getBottomWashMinAngle()).ifPresent(value -> builder.setBottomWashMinAngle(value.toString()));
                replyBuilder.addVesselCowParameters(builder.build());
            }
            log.info("Vessel Cow Parameters set for Size - {}", vcp.size());
        }
    }
}
