/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "loadable_pattern")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePattern extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "case_number")
  private Integer caseNumber;

  @Column(name = "constraints")
  private String constraints;

  @Column(name = "loadable_study_status")
  private Integer loadableStudyStatus;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "loadablePatternXId")
  private Collection<LoadablePlan> loadablePlanCollection;

  @JoinColumn(name = "loadablestudy_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadableStudy loadableStudy;
}
