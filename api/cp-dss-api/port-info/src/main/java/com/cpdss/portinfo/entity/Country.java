/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "country")
public class Country extends EntityDoc {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
  private List<PortInfo> portInfo;
}
