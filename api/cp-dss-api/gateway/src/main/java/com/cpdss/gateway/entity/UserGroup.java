/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
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
@Table(name = "users")
public class UserGroup extends EntityDoc {

  @Column(name = "group_name")
  private String groupName;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
  private List<GroupUserMapping> groupUserMappingList;
}
