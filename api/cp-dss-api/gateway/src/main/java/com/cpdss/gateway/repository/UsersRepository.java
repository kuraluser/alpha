/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/**
 * User repository - to interact with {@link Users} table
 *
 * @author suhail.k
 */
public interface UsersRepository extends CommonCrudRepository<Users, Long> {

  public List<Users> findByIdIn(List<Long> idList);

  public Users findByKeycloakIdAndIsActive(String keycloakId, Boolean isActive);

  public List<Users> findByCompanyXIdAndIsActive(Long companyXId, Boolean isActive);

  @Query("FROM Users US WHERE US.id =?1 AND US.isActive = ?2")
  public Optional<Users> findByIdIdAndIsActive(Long userId, boolean b);
}
