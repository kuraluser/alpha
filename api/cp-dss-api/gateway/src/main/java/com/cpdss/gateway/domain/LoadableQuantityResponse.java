package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;

import lombok.Data;

/**
 * @Author jerin.g
 *
 */
@Data
public class LoadableQuantityResponse {
	private CommonSuccessResponse commonSuccessResponse;
	private Long loadableQuantityId;
}
