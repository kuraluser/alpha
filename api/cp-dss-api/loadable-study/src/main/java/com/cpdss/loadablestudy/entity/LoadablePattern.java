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
@Table(name = "loadablepattern")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePattern extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "casenumber")
  private Integer caseNumber;

  @Column(name = "constraints")
  private String constraints;

  @Column(name = "loadablestudystatus")
  private Integer loadableStudyStatus;

  @Column(name = "isactive")
  private Boolean isActive;

  @OneToMany(mappedBy = "loadablePatternXId")
  private Collection<LoadablePlan> loadablePlanCollection;

  @JoinColumn(name = "loadablestudyxid", referencedColumnName = "id")
  @ManyToOne
  private LoadableStudy loadableStudyXid;
}
