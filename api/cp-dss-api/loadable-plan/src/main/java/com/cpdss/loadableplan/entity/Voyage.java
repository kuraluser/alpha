/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.entity;

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

  @Column(name = "vesselxid")
  private Long vesselXId;

  @Column(name = "voyageno")
  private String voyageNo;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "companyxid")
  private Long companyXId;

  @Column(name = "voyagestatus")
  private Integer voyageStatus;

  @Column(name = "captainxid")
  private Long captainXId;

  @Column(name = "chiefofficerxid")
  private Long chiefOfficerXId;
}
