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
@Table(name = "algo_error_heading")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlgoErrorHeading extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "error_heading")
  private String errorHeading;

  @ManyToOne
  @JoinColumn(name = "loading_information_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @Column(name = "is_active")
  private Boolean isActive;
}
