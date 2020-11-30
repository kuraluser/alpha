/* Licensed under Apache-2.0 */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@Table(name = "screen")
public class Screen extends EntityDoc {

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "language_key")
  private String languageKey;

  @Column(name = "is_available_add")
  private Boolean isAvailableAdd;

  @Column(name = "is_available_edit")
  private Boolean isAvailableEdit;

  @Column(name = "is_available_delete")
  private Boolean isAvailableDelete;

  @Column(name = "is_available_view")
  private Boolean isAvailableView;

  @Column(name = "isactive")
  private Boolean isActive;

  @OneToMany(mappedBy = "screen")
  private Collection<RoleScreen> roleScreenList;
}
