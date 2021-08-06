package com.cpdss.loadablestudy.domain;

import lombok.Data;

import java.util.List;

/** @Author gokul.p */
@Data
public class LoadableStudyAlgoJson {
    private String processId;
    private List<LoadablePlanDetailsAlgoJson> loadablePlanDetails;
    private Object errors;
    private boolean hasLodicator;
    private Object validated;
    private Object loadablePatternId;
    private Object hasLoadicator;
}
