/* Licensed at AlphaOri Technologies */
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
@Table(name = "loadable_plan_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanComments extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "comments")
  private String comments;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadable_pattern_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePattern;
}
