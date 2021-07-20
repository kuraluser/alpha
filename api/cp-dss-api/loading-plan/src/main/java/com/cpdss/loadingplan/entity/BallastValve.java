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
@Table(name = "ballast_valves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BallastValve extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loading_sequence_xid", referencedColumnName = "id")
  private LoadingSequence loadingSequence;

  @Column(name = "time")
  private Integer time;

  @Column(name = "operation")
  private String operation;

  @Column(name = "valve_code")
  private String valveCode;

  @Column(name = "valve_xid")
  private Long valveXId;

  @Column(name = "valve_type")
  private String valveType;

  @Column(name = "is_active")
  private Boolean isActive;
}
