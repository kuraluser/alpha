/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.Roles;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<Roles, Long> {

  Optional<Roles> findByIdAndIsActive(Long roleId, boolean isActive);

  Optional<Roles> findByNameAndIsActive(String name, boolean isActive);
}
