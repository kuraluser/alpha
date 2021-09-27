/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.RoleUserMapping;
import java.util.List;
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

  @Query("FROM RoleUserMapping RM WHERE RM.roles.id = ?1 and RM.isActive = ?2")
  List<RoleUserMapping> findByRolesAndIsActive(Long roleId, boolean b);

  @Query("FROM RoleUserMapping RM WHERE RM.roles.id = ?1")
  List<RoleUserMapping> findByRoleId(Long roleId);

  @Query("FROM RoleUserMapping RM WHERE RM.users.id IN ?1")
  List<RoleUserMapping> findByUserIds(List<Long> userIds);
  
  @Query("FROM RoleUserMapping RM WHERE RM.users.id IN ?1 AND RM.isActive = ?2")
  List<RoleUserMapping> findByUserIdsAndIsActive(List<Long> userIds, boolean b);

  //  @Query("FROM RoleUserMapping RM WHERE RM.roles.id = ?1 AND RM.users.id in (:userIds)")
  //  List<RoleUserMapping> findByRolesAndUsersInIsActive(@Param("roleId") Long roleId,
  // @Param("userIds") List<Long> userIds);

  @Query("FROM RoleUserMapping RM WHERE RM.users.id = ?1 and RM.isActive = ?2 and RM.roles.id = ?3")
  Optional<RoleUserMapping> findByUsersAndIsActive(Long userId, boolean b, Long roleId);
}
