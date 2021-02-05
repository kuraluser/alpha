/* Licensed under Apache-2.0 */
package com.cpdss.loadicatorintegration.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ld_stowage_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StowagePlan extends EntityDoc {

  @Column(name = "vessel_xid")
  private Long vesselXId;

  @Column(name = "imo_number", length = 100)
  private String imoNumber;

  @Column(name = "company_xid")
  private Long companyXId;

  @Column(name = "ship_type")
  private Long shipType;

  @Column(name = "vessel_code", length = 100)
  private String vesselCode;

  @Column(name = "bookinglist_id")
  private Long bookingListId;

  @Column(name = "stowage_id")
  private Long stowageId;

  @Column(name = "port_id")
  private Long portId;

  @Column(name = "port_code", length = 100)
  private String portCode;

  @Column(name = "status")
  private Long status;

  @Column(name = "damage_cal")
  private Boolean damageCal;

  @Column(name = "deadweight_constant")
  private Long deadweightConstant;

  @Column(name = "provisional_constant")
  private Long provisionalConstant;

  @Column(name = "cal_count")
  private Long calCount;

  @Column(name = "data_save")
  private Boolean dataSave;

  @Column(name = "save_status", length = 100)
  private String saveStatus;

  @Column(name = "save_message", length = 100)
  private String saveMessage;
}