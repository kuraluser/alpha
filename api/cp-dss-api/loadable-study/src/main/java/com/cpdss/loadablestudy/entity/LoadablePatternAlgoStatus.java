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
@Table(name = "loadable_pattern_algo_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePatternAlgoStatus extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "vesselxid")
  private Long vesselxid;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "process_id")
  private String processId;

  @JoinColumn(name = "loadabale_pattern_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePattern;

  @JoinColumn(name = "loadable_study_status", referencedColumnName = "id")
  @ManyToOne
  private LoadableStudyStatus loadableStudyStatus;
}
