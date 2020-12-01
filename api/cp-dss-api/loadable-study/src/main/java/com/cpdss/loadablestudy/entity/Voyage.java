/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "voyage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voyage extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @Column(name = "vessel_xid")
  private Long vesselXId;

  @Column(name = "voyage_no")
  private String voyageNo;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "company_xid")
  private Long companyXId;

  @Column(name = "voyage_status")
  private Integer voyageStatus;

  @Column(name = "captain_xid")
  private Long captainXId;

  @Column(name = "chiefofficer_xid")
  private Long chiefOfficerXId;
}
