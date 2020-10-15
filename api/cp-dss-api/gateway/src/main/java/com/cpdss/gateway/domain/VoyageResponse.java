package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;

import lombok.Data;

/**
 * @Author jerin.g
 *
 */
@Data
public class VoyageResponse {
	private CommonSuccessResponse responseStatus;
	private Long voyageId;
}
