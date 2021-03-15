/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenData {
  private Long id;
  private String name;
  private RoleScreen roleScreen;
  private Long moduleId;
  List<ScreenData> childs;
  private Boolean isAddVisible;
  private Boolean isEditVisible;
  private Boolean isDeleteVisible;
  private Boolean isViewVisible;
}
