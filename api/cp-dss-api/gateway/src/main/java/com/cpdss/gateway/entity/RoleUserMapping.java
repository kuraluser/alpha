/* Licensed at AlphaOri Technologies */
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
@Table(name = "role_user_mapping")
public class RoleUserMapping extends EntityDoc {

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "user_xid")
  private Users users;

  @ManyToOne
  @JoinColumn(name = "role_xid")
  private Roles roles;
}
