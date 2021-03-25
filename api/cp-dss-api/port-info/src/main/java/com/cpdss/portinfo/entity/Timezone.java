/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Timezone extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "company_xid")
  private Long companyId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "offset_value")
  private String offsetValue;

  @Column(name = "timezone")
  private String timezone;

  @Column(name = "region")
  private String region;
  
  // bi-directional many-to-one association to PortInfo
  @OneToMany(mappedBy = "timezone")
  private List<PortInfo> portInfos;
}
