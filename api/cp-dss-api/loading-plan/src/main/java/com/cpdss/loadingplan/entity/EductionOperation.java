/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

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

/** @author pranav.k */
@Entity
@Table(name = "eduction_operation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EductionOperation extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loading_sequences_xid", referencedColumnName = "id", nullable = true)
  private LoadingSequence loadingSequence;

  @Column(name = "time_start")
  private Integer startTime;

  @Column(name = "time_end")
  private Integer endTime;

  @Column(name = "tanks_used")
  private String tanksUsed;

  @Column(name = "eductor_used")
  private String eductorsUsed;

  @Column(name = "ballast_pumps_used")
  private String ballastPumpsUsed;

  @Column(name = "is_active")
  private Boolean isActive;
}
