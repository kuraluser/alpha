/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "vessel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vessel extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "port_of_registry")
  private String portOfRegistry;

  @Column(name = "owner_xid")
  private Integer ownerXid;

  @Column(name = "builder")
  private String builder;

  @Column(name = "class")
  private String class1;

  @Column(name = "vessel_type_xid")
  private Integer vesselTypeXid;

  @Column(name = "official_number")
  private String officialNumber;

  @Column(name = "signal_letter")
  private String signalLetter;

  @Column(name = "imo_number")
  private String imoNumber;

  @Column(name = "navigation_area_xid")
  private Integer navigationAreaId;

  @Column(name = "type_of_ship")
  private String typeOfShip;

  @Column(name = "date_keel_laid")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateKeelLaid;

  @Column(name = "date_of_launching")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateOfLaunching;

  @Column(name = "date_of_delivery")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateOfDelivery;

  @Column(name = "register_length")
  private BigDecimal registerLength;

  @Column(name = "length_overall")
  private BigDecimal lengthOverall;

  @Column(name = "length_between_perpendiculars")
  private BigDecimal lengthBetweenPerpendiculars;

  @Column(name = "breadth_molded")
  private BigDecimal breadthMolded;

  @Column(name = "depth_molded")
  private BigDecimal depthMolded;

  @Column(name = "designed_loaddraft")
  private BigDecimal designedLoaddraft;

  @Column(name = "draft_full_load_summer")
  private BigDecimal draftFullLoadSummer;

  @Column(name = "thickness_of_upper_deck_stringer_plate")
  private BigDecimal thicknessOfUpperDeckStringerPlate;

  @Column(name = "thickness_of_keelplate")
  private BigDecimal thicknessOfKeelplate;

  @Column(name = "deadweight")
  private BigDecimal deadweight;

  @Column(name = "lightweight")
  private BigDecimal lightweight;

  @Column(name = "company_xid")
  private Long companyXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "profile_image_path")
  private String profileImagePath;

  @Column(name = "lcg")
  private BigDecimal lcg;

  @Column(name = "keel_to_mast_height")
  private BigDecimal keelToMastHeight;

  @Column(name = "deadweight_constant")
  private BigDecimal deadweightConstant;

  @Column(name = "provisional_constant")
  private BigDecimal provisionalConstant;

  @Column(name = "deadweight_constant_lcg")
  private BigDecimal deadweightConstantLcg;

  @Column(name = "provisional_constant_lcg")
  private BigDecimal provisionalConstantLcg;

  @Column(name = "gross_tonnage")
  private BigDecimal grossTonnage;

  @Column(name = "net_tonnage")
  private BigDecimal netTonnage;

  @Column(name = "deadweight_constant_tcg")
  private BigDecimal deadweightConstantTcg;

  @Column(name = "has_center_cofferdam")
  private Boolean hasCenterCofferdam;

  @Column(name = "frame_space_3l")
  private BigDecimal frameSpace3l;

  @Column(name = "frame_space_7l")
  private BigDecimal frameSpace7l;

  @Column(name = "has_loadicator")
  private Boolean hasLoadicator;

  @Column(name = "master_xid")
  private Long masterId;

  @Column(name = "chiefofficer_xid")
  private Long chiefofficerId;

  @Column(name = "chiefengineer_xid")
  private Long chiefengineerId;

  @JoinColumn(name = "flag_xid", referencedColumnName = "id")
  @ManyToOne
  private VesselFlag vesselFlag;

  @OneToMany(mappedBy = "vessel")
  private Set<VesselDraftCondition> vesselDraftConditionCollection;

  @OneToMany(mappedBy = "vessel", fetch = FetchType.LAZY)
  private Set<UllageTrimCorrection> ullageTrimCorrections;
}
