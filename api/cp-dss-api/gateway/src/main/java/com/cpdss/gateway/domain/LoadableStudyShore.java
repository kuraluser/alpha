package com.cpdss.gateway.domain;


import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author ravi.r
 */
@Data
public class LoadableStudyShore {

    private Long id;

    private String vesselName;

    private Long imoNo;

    private String flagName;

    private String atd;

    private String eta;

    private String voyageName;

    private List<VoyagePorts> voyagePorts;
}
