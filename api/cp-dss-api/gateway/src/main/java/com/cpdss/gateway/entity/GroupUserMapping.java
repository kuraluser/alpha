/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_user_mapping")
public class GroupUserMapping extends EntityDoc {

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_xid")
  private Users users;

  @Column(name = "group_xid")
  private String groupXId;
}
