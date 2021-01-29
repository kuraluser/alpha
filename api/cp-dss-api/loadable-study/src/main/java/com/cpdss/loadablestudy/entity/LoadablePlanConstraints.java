/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

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

/** @author jerin.g */
@Entity
@Table(name = "loadable_plan_constraints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanConstraints extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @JoinColumn(name = "loadable_pattern_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePattern;

  @Column(name = "constraints_data")
  private String constraintsData;

  @Column(name = "is_active")
  private Boolean isActive;
}
