/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author pranav.k */
@Entity
@Table(name = "port_wise_time_required_for_loading")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortWiseTimeRequiredForLoading extends EntityDoc {

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternXId;

  @OneToOne
  @JoinColumn(name = "port_rotation_xid", referencedColumnName = "id")
  private LoadableStudyPortRotation portRotation;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "port_code")
  private String portCode;

  @Column(name = "time_required_for_loading")
  private BigDecimal timeRequiredForLoading;

  @Column(name = "is_active")
  private Boolean isActive;

  @Transient private Long communicationRelatedEntityId;
}
