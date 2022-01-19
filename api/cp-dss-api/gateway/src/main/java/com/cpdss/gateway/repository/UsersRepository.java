/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.UserStatus;
import com.cpdss.gateway.entity.Users;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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

  public Users findByKeycloakId(String keycloakId);

  public List<Users> findByIsActive(boolean isActive);

  public List<Users> findByIsActiveAndStatusOrderById(boolean isActive, UserStatus status);

  @Query(
      value =
          "SELECT u.id , u.first_name , u.last_name , u.user_name , u.designation , r.name, u.is_ship_user FROM "
              + "public.users u left join role_user_mapping m on u.id = m.user_xid and m.is_active = true "
              + "left join roles r on r.id = m.role_xid "
              + "where u.user_status_xid = 1 and u.is_active = true order by id desc ",
      nativeQuery = true)
  public List<Object[]> findByIsActiveAndStatusOrderByIdAndRole(boolean isActive, Long id);

  public List<Users> findByKeycloakIdInAndStatusAndIsActiveOrderById(
      List<String> keycloakIds, UserStatus status, boolean isActive);

  public List<Users> findByKeycloakIdInOrderById(List<String> keycloakIds);

  public List<Users> findByKeycloakIdInAndIsActiveTrueOrderById(List<String> keycloakIds);

  public List<Users> findByCompanyXIdAndIdInAndIsActive(
      Long companyXId, List<Long> userId, boolean isActive);

  public List<Users> findByCompanyXIdAndIdIn(Long companyXId, List<Long> userId);

  public Users findByIdAndIsActive(Long id, boolean isActive);

  @Query("FROM Users us where us.id in ?1 and us.isActive = ?2")
  public List<Users> findByIdInAndIsActive(Set<Long> ids, boolean isActive);

  public Users findByUsernameAndIsActive(String username, boolean isActive);

  Users findByUsernameIgnoreCaseAndIsActive(String username, boolean isActive);

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
