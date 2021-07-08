package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Master Service for Loadable Pattern
 *
 * @author vinothkumar M
 * @since 08-07-2021
 */
@Slf4j
@Service
public class LoadablePatternService {

    private static final Long CONFIRMED_STATUS_ID = 2L;

    private static final Long LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID = 3L;

    @Autowired private LoadablePatternRepository loadablePatternRepository;

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

}
