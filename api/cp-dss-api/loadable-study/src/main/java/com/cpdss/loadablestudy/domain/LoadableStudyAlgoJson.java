package com.cpdss.loadablestudy.domain;

import lombok.Data;

import java.util.List;

/** @Author gokul.p */
@Data
public class LoadableStudyAlgoJson {
    private String processId;
    private List<LoadablePlanDetailsAlgoJson> loadablePlanDetails;
    private String errors;
}
