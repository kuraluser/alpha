package com.cpdss.loadablestudy.domain;
import lombok.Data;

import java.util.List;

/** @Author gokul.p */
@Data
public class LoadablePlanDetailsAlgoJson {
    private Long caseNumber;
    private List<LoadablePlanPortWiseDetailsAlgoJson> loadablePlanPortWiseDetails;
    private Object constraints;
    private Double slopQuantity;
    private Object stabilityParameters;
}
