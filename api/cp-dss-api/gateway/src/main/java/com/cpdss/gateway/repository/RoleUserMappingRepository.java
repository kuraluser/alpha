/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Roles;
import com.cpdss.gateway.entity.Users;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RoleUserMappingRepository extends CrudRepository<RoleUserMapping, Long> {

  public List<RoleUserMapping> findByUsersAndIsActive(Users user, boolean isActive);
  
  public List<RoleUserMapping> findByRolesAndIsActive(Roles roles, boolean isActive);
}
