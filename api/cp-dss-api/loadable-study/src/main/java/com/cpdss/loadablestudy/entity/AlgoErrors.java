/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "algo_errors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlgoErrors extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @Column(name = "error_message")
  private String errorMessage;

  // bi-directional many-to-one association to AlgoErrorHeading
  @ManyToOne
  @JoinColumn(name = "error_heading_xid", referencedColumnName = "id")
  private AlgoErrorHeading algoErrorHeading;

  @Column(name = "is_active")
  private Boolean isActive;
}
