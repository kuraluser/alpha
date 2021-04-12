/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "algo_error_heading")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlgoErrorHeading extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @Column(name = "error_heading")
  private String errorHeading;

  // bi-directional many-to-one association to AlgoError
  @OneToMany(mappedBy = "algoErrorHeading")
  private List<AlgoErrors> algoErrors;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadable_pattern_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePattern;

  @JoinColumn(name = "loadable_study_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadableStudy loadableStudy;
}
