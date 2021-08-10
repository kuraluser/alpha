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

@Entity
@Table(name = "algo_errors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlgoErrors extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "error_heading_xid", referencedColumnName = "id")
  private AlgoErrorHeading algoErrorHeading;

  @Column(name = "error_message")
  private String errorMessage;

  @Column(name = "is_active")
  private Boolean isActive;
}
