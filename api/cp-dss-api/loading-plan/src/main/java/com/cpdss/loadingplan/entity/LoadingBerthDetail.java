/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "loading_berth_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadingBerthDetail extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loading_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @Column(name = "berth_xid")
  private Long berthXId;

  @Column(name = "depth")
  private BigDecimal depth;

  @Column(name = "sea_draft_limtation")
  private BigDecimal seaDraftLimitation;

  @Column(name = "air_draft_limitation")
  private BigDecimal airDraftLimitation;

  @Column(name = "max_manifold_height")
  private BigDecimal maxManifoldHeight;

  @Column(name = "special_regulation_restriction")
  private String specialRegulationRestriction;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "hose_connections")
  private String hoseConnections;

  @Column(name = "item_to_be_agreed")
  private String itemToBeAgreedWith;

  @Column(name = "line_content_displacement")
  private BigDecimal lineDisplacement;
}
