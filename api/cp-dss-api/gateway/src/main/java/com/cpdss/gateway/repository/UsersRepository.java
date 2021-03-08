/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.Users;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
  @Query(
      "update Users us set us.userPassword = :password, us.passwordExpiryDate = :expD, us.passwordUpdateDate = :upD where us.id = :id")
  int updateUserPasswordExpireDateAndTime(
      @Param("id") Long id,
      @Param("password") String password,
      @Param("expD") LocalDateTime expD,
      @Param("upD") LocalDateTime upD);

  @Modifying
  @Query("update Users us set us.isActive = false where us.id = :id")
  int updateUserIsActiveToFalse(@Param("id") Long id);
}
