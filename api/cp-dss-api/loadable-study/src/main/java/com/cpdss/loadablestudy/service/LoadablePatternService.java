package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanCommingleDetails;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanCommingleDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;

/**
 * Master Service for Loadable Pattern
 *
 * @author vinothkumar M
 * @since 08-07-2021
 */
@Slf4j
@Service
public class LoadablePatternService {

    @Autowired private LoadablePatternRepository loadablePatternRepository;

    @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

    /**
     *
     * @param loadableStudy
     * @throws GenericServiceException
     */
    public void isPatternGeneratedOrConfirmed(LoadableStudy loadableStudy)
            throws GenericServiceException {
        List<LoadablePattern> generatedPatterns =
                this.loadablePatternRepository.findLoadablePatterns(
                        LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);
        List<LoadablePattern> confirmedPatterns =
                this.loadablePatternRepository.findLoadablePatterns(
                        CONFIRMED_STATUS_ID, loadableStudy, true);
        if (!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty()) {
            throw new GenericServiceException(
                    "Save/Edit/Delte not allowed for plan generated /confirmed loadable study",
                    CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
                    HttpStatusCode.BAD_REQUEST);
        }
    }

    public void getLoadableCommingleByPatternId(com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request, com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply.Builder builder) throws Exception {
        Optional<LoadablePattern> loadablePatternOpt =
                this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
        if (loadablePatternOpt.isPresent()) {
            List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails =
                    this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
                            loadablePatternOpt.get(), true);
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder loadableCommingle =
                    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder();
            loadablePlanCommingleDetails.forEach(
                    lpcd -> {
                        com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder loadableQtyCommCargo =
                                getCommingleCargoBuilder(lpcd);
                        builder.addLoadableQuantityCommingleCargoDetails(loadableQtyCommCargo);
                    });
            builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
        } else throw new Exception("Cannot find loadable pattern");
        builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    }

    private com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder getCommingleCargoBuilder(
            LoadablePlanCommingleDetails lpcd) {
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder builder =
                com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder();
        ofNullable(lpcd.getId()).ifPresent(builder::setId);
        ofNullable(lpcd.getApi()).ifPresent(builder::setApi);
        ofNullable(lpcd.getCargo1Abbreviation()).ifPresent(builder::setCargo1Abbreviation);
        ofNullable(lpcd.getCargo1Bbls60f()).ifPresent(builder::setCargo1Bbls60F);
        ofNullable(lpcd.getCargo1BblsDbs()).ifPresent(builder::setCargo1Bblsdbs);
        ofNullable(lpcd.getCargo1Kl()).ifPresent(builder::setCargo1KL);
        ofNullable(lpcd.getCargo1Lt()).ifPresent(builder::setCargo1LT);
        ofNullable(lpcd.getCargo1Mt()).ifPresent(builder::setCargo1MT);
        ofNullable(lpcd.getCargo1Percentage()).ifPresent(builder::setCargo1Percentage);
        ofNullable(lpcd.getCargo2Abbreviation()).ifPresent(builder::setCargo2Abbreviation);
        ofNullable(lpcd.getCargo2Bbls60f()).ifPresent(builder::setCargo2Bbls60F);
        ofNullable(lpcd.getCargo2BblsDbs()).ifPresent(builder::setCargo2Bblsdbs);
        ofNullable(lpcd.getCargo2Kl()).ifPresent(builder::setCargo2KL);
        ofNullable(lpcd.getCargo2Lt()).ifPresent(builder::setCargo2LT);
        ofNullable(lpcd.getCargo2Mt()).ifPresent(builder::setCargo2MT);
        ofNullable(lpcd.getCargo2Percentage()).ifPresent(builder::setCargo2Percentage);
        ofNullable(lpcd.getGrade()).ifPresent(builder::setGrade);
        ofNullable(lpcd.getQuantity()).ifPresent(builder::setQuantity);
        ofNullable(lpcd.getTankName()).ifPresent(builder::setTankName);
        ofNullable(lpcd.getTemperature()).ifPresent(builder::setTemp);
        ofNullable(lpcd.getTankShortName()).ifPresent(builder::setTankShortName);
        return builder;
    }
}
