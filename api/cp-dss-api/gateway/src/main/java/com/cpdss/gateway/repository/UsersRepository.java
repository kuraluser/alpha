/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * User repository - to interact with {@link Users} table
 *
 * @author suhail.k
 */
public interface UsersRepository extends CommonCrudRepository<Users, Long> {

  public List<Users> findByIdIn(List<Long> idList);

  public Users findByKeycloakIdAndIsActive(String keycloakId, Boolean isActive);

  public List<Users> findByIsActive(boolean isActive);

  public List<Users> findByIsActiveOrderById(boolean isActive);

  public List<Users> findByCompanyXIdAndIdInAndIsActive(
      Long companyXId, List<Long> userId, boolean isActive);

  public Users findByIdAndIsActive(Long id, boolean isActive);

  public Users findByUsernameAndIsActive(String username, boolean isActive);

  @Modifying
  @Query("update Users us set us.userPassword = :password where us.id = :id")
  int updateUserPasswordByUserId(String password, Long id);
}
