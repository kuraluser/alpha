/* Licensed under Apache-2.0 */
package com.cpdss.companyinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Company data entity
 *
 * @author suhail.k
 */
@Entity
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company extends EntityDoc {

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "address", length = 500)
  private String address;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "phone", length = 50)
  private String phone;

  @Column(name = "website", length = 50)
  private String website;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "key_cloak_idp", length = 100)
  private String keycloakIdp;

  @Column(name = "realm", length = 50)
  private String realm;

  @Column(name = "domain", length = 100)
  private String domain;

  @Column(name = "company_logo", length = 200)
  private String companyLogo;

  @OneToMany(mappedBy = "company")
  private Set<Carousals> carousals;
}
