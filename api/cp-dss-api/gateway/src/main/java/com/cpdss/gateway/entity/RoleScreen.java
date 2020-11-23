/* Licensed under Apache-2.0 */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleScreen extends EntityDoc {

  @Column(name = "canadd")
  private Boolean canAdd;

  @Column(name = "canedit")
  private Boolean canEdit;

  @Column(name = "candelete")
  private Boolean canDelete;

  @Column(name = "canview")
  private Boolean canView;

  @Column(name = "isactive")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "rolexid")
  private Roles roles;

  @ManyToOne
  @JoinColumn(name = "screenxid")
  private Screen screen;
}
