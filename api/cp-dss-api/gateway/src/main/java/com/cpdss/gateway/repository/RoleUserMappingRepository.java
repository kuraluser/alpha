/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Roles;
import com.cpdss.gateway.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleUserMappingRepository extends CrudRepository<RoleUserMapping, Long> {

  public List<RoleUserMapping> findByUsersAndIsActive(Users user, boolean isActive);

  public List<RoleUserMapping> findByRolesAndIsActive(Roles roles, boolean isActive);

  @Modifying
  @Query("UPDATE RoleUserMapping SET isActive=false WHERE users.id=?1 and isActive=true")
  @Transactional
  public void deleteRolesByUser(Long userId);
}
