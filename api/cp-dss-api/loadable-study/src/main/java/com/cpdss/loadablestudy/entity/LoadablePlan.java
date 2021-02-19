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
@Table(name = "loadable_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlan extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "loadable_study_xid")
  private Long loadableStudyXId;

  @Column(name = "details")
  private String details;

  @Column(name = "comments")
  private String comments;

  @Column(name = "loadable_plan_status")
  private Long loadablePlanStatus;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadable_pattern_XId", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePatternXId;
}
