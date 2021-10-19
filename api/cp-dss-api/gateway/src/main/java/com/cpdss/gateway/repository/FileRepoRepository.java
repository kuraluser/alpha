/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.FileRepo;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

/**
 * File repository - to interact with {@link FileRepo} table
 *
 * @author gokul.p
 */
@Transactional
public interface FileRepoRepository extends CommonCrudRepository<FileRepo, Long>, JpaSpecificationExecutor<FileRepo> {
  Page<FileRepo> findAllByIsActiveTrueIgnoreCase(@Nullable Specification<FileRepo> spec, Pageable pageable);
}
