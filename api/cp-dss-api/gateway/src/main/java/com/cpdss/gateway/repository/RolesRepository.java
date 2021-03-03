/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolesRepository
    extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {

  public List<Roles> findByCompanyXIdAndIsActive(Long companyXId, Boolean isActive);

  Optional<Roles> findByIdAndCompanyXIdAndIsActive(Long roleId, Long companyXId, boolean isActive);

  Optional<Roles> findByCompanyXIdAndNameAndIsActive(
      Long companyXId, String name, boolean isActive);

  public Roles findByNameIgnoreCaseAndIsActive(String name, boolean isActive);
  
  public List<Roles> findByIsActiveOrderByName(Boolean isActive);
}
