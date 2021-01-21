/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleScreen {

  private Long id;
  private Long role;
  private Long screen;
  private Boolean canAdd;
  private Boolean canEdit;
  private Boolean canView;
  private Boolean canDelete;
  private Boolean canPrint;
  private Long companyId;
}
