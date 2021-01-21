/* Licensed under Apache-2.0 */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_screen")
public class RoleScreen extends EntityDoc {

  @Column(name = "can_add")
  private Boolean canAdd;

  @Column(name = "can_edit")
  private Boolean canEdit;

  @Column(name = "can_delete")
  private Boolean canDelete;

  @Column(name = "can_view")
  private Boolean canView;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_xid")
  private Roles roles;

  @ManyToOne
  @JoinColumn(name = "screen_xid")
  private Screen screen;

  @Column(name = "company_xid")
  private Long companyXId;
}
