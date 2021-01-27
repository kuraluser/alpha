/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;

import com.cpdss.common.rest.CommonSuccessResponse;

import lombok.Data;

@Data
public class RoleResponse {
	private List<Role> users;
	private CommonSuccessResponse responseStatus;
	private Long roleId;
	private String message;
}
