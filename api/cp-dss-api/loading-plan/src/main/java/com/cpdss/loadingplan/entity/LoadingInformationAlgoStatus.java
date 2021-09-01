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
@Table(name = "loading_information_algo_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingInformationAlgoStatus extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "vesselxid")
  private Long vesselXId;

  @JoinColumn(name = "loading_information_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadingInformation loadingInformation;

  @JoinColumn(name = "loading_information_status_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadingInformationStatus loadingInformationStatus;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "process_id")
  private String processId;

  @Column(name = "type_xid")
  private Integer conditionType;
}
