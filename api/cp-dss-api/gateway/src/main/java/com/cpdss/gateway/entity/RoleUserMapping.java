/* Licensed under Apache-2.0 */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roleusermapping")
public class RoleUserMapping extends EntityDoc {

  @Column(name = "IsActive")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "userxid")
  private Users users;

  @ManyToOne
  @JoinColumn(name = "rolexid")
  private Roles roles;
}
