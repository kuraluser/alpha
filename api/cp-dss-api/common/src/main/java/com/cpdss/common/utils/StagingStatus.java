package com.cpdss.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StagingStatus {
    DRAFT("draft"),
    READY_TO_PROCESS("ready_to_process"),
    IN_PROGRESS("in_progress"),
    UNABLE_TO_PROCESS("unable_to_process"),
    ERROR("error"),
    RETRY("retry"),
    FAILED("failed"),
    COMPLETED("completed");

    private final String status;
}
