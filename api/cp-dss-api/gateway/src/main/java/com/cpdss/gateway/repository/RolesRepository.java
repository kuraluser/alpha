/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<Roles, Long> {

  public List<Roles> findByCompanyXIdAndIsActive(Long companyXId, Boolean isActive);

  public Optional<Roles> findByIdAndCompanyXIdAndIsActive(
      Long id, Long companyXId, Boolean isActive);

  Optional<Roles> findByIdAndIsActive(Long roleId, boolean isActive);

  Optional<Roles> findByCompanyXIdAndNameAndIsActive(
      Long companyXId, String name, boolean isActive);

  Optional<Roles> findByNameAndIsActive(String name, boolean isActive);
}
