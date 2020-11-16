/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.Users;
import java.util.List;

/**
 * User repository - to interact with {@link Users} table
 *
 * @author suhail.k
 */
public interface UsersRepository extends CommonCrudRepository<Users, Long> {

  public List<Users> findByIdIn(List<Long> idList);
}
