/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadingInstructionsUpdateRequest {
    private List<LoadingInstructionsStatus> instructionList;
}
