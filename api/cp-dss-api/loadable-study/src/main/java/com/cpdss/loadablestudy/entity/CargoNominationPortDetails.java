/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for Cargo Nomination Port Details */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cargo_nomination_operation_details")
public class CargoNominationPortDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "cargo_nomination_xid")
  private CargoNomination cargoNomination;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "mode_xid")
  private Long mode;

  @Column(name = "operation_xid")
  private Long operationId;

  @Column(name = "sequence_no")
  private Long sequenceNo;

  @Column(name = "empty_max_no_of_tanks")
  private Boolean emptyMaxNoOfTanks;

  @OneToOne
  @JoinColumn(name = "port_rotation_xid", referencedColumnName = "id")
  private LoadableStudyPortRotation portRotation;

  @Transient private Long communicationRelatedEntityId;
}
