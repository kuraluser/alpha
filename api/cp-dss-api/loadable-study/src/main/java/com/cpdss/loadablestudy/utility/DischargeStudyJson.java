/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.utility;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.FAILED;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.INVALID_LOADABLE_STUDY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadablestudy.domain.DischargeStudyAlgoJson;
import com.cpdss.loadablestudy.domain.LoadableStudyInstruction;
import com.cpdss.loadablestudy.domain.LoadableStudyPortRotationJson;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.PortInstruction;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.PortInstructionRepository;
import com.cpdss.loadablestudy.service.LoadableStudyPortRotationService;

public class DischargeStudyJson {

	@Autowired
	PortInstructionRepository portInstructionRepository;
	
	@Autowired
	LoadableStudyRepository loadableStudyRepository;
	
	@Autowired LoadableStudyPortRotationService loadableStudyPortRotationService;

	public void generateJson(Long dischargeStudyId,Long voyageId,String voyageNo,Long vesselId, String dischargeName) {
		
		DischargeStudyAlgoJson dischargeStudyAlgoJson = new DischargeStudyAlgoJson();
		
	    Optional<LoadableStudy> loadableStudy =
	            this.loadableStudyRepository.findByIdAndIsActive(dischargeStudyId, true);

	    if (!loadableStudy.isPresent()) {
//	          log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
//	          portRotationReplyBuilder.setResponseStatus(
//	              Common.ResponseStatus.newBuilder()
//	                  .setStatus(FAILED)
//	                  .setMessage(INVALID_LOADABLE_STUDY_ID)
//	                  .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
	        } else {
	        	
	    

		dischargeStudyAlgoJson.setId(dischargeStudyId);
		dischargeStudyAlgoJson.setVoyageId(voyageId);
		dischargeStudyAlgoJson.setVoyageNo(voyageNo);
		dischargeStudyAlgoJson.setVesselId(vesselId);
		dischargeStudyAlgoJson.setName(dischargeName);
		
		dischargeStudyAlgoJson.setInstructionMaster(getAllLoadableStudyInstruction());
		dischargeStudyAlgoJson.setCommingleCargos(new ArrayList<>()); //reserved for future.Keeping empty for now 
		dischargeStudyAlgoJson.setDischargeStudyPortRotation(getDischargeStudyPortRotation());
	        }
	}

	private List<LoadableStudyPortRotationJson> getDischargeStudyPortRotation() {
		List<LoadableStudyPortRotationJson> portRotationList = new ArrayList<>();
		PortRotationRequest.Builder request = PortRotationRequest.newBuilder();
		PortRotationReply.Builder reply = PortRotationReply.newBuilder();
		loadableStudyPortRotationService.getPortRotationByLoadableStudyId(request.build(), reply);
	}

	//Getting all instructions from master table PortInstruction
	private List<LoadableStudyInstruction> getAllLoadableStudyInstruction() {

		List<PortInstruction> instructionsDetails = portInstructionRepository.findByIsActive(true);
		return instructionsDetails.stream()
				.map(item -> new LoadableStudyInstruction(item.getId(), item.getPortInstruction()))
				.collect(Collectors.toList());
	}
}
