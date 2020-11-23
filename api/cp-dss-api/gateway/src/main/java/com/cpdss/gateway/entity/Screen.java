/* Licensed under Apache-2.0 */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Screen extends EntityDoc {

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "languagekey")
  private String languageKey;

  @Column(name = "isavailableadd")
  private Boolean isAvailableAdd;

  @Column(name = "isavailableedit")
  private Boolean isAvailableEdit;

  @Column(name = "isavailabledelete")
  private Boolean isAvailableDelete;

  @Column(name = "isavailableview")
  private Boolean isAvailableView;

  @Column(name = "isactive")
  private Boolean isActive;

  @OneToMany(mappedBy = "screen")
  private Collection<RoleScreen> roleScreenList;
}
