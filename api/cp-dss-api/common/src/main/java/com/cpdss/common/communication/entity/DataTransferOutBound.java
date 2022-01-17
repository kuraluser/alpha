/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

/**
 * DataTransferOutBound entity
 *
 * @author Mahesh KM
 */
@Entity
@Table(name = "data_transfer_out_bound")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTransferOutBound extends EntityDoc {

  private static final long serialVersionUID = 1L;

  // LoadableStudy,Voyage,LoadingPlan,...
  @Column(name = "reference")
  private String reference;

  @Column(name = "reference_id")
  private Long referenceId;

  @Column(name = "is_communicated")
  private Boolean isCommunicated;
}
