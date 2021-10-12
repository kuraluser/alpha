package com.cpdss.gateway.domain.filerepo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/** @Author gokul.p */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FileRepoRequest {
    private String voyageNo;
    private String fileName;
    private String fileType;
    private String section;
    private String category;
}
