/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "algo_errors")
public class AlgoErrors extends EntityDoc {

  @Column(name = "error_message")
  private String errorMessage;

  // bi-directional many-to-one association to AlgoErrorHeading
  @ManyToOne
  @JoinColumn(name = "error_heading_xid")
  private AlgoErrorHeading algoErrorHeading;
}
