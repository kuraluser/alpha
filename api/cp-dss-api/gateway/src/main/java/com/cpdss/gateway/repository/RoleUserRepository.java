/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.RoleUserMapping;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleUserRepository extends CrudRepository<RoleUserMapping, Long> {

  @Query("FROM RoleUserMapping RM WHERE RM.users.id = ?1 and RM.roles.id = ?2 and RM.isActive = ?3")
  Optional<RoleUserMapping> findByUsersAndRolesAndIsActive(
      Long userId, Long roleId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("Update RoleUserMapping RM set RM.isActive = false where RM.roles.id = ?1 ")
  public void deleteRoles(Long id);
}
