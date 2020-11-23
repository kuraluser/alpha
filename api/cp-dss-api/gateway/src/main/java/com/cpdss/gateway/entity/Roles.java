/* Licensed under Apache-2.0 */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Roles extends EntityDoc {

  @Column(name = "name")
  private String name;

  @OneToMany(
      mappedBy = "roles",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private Collection<RoleUserMapping> roleUserMappings;

  @OneToMany(mappedBy = "roles")
  private Collection<Users> users;

  @OneToMany(mappedBy = "roles")
  private Collection<RoleScreen> roleScreens;
}
