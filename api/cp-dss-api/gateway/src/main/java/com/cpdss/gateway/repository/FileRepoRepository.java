package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.FileRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

/**
* User repository - to interact with {@link FileRepo} table
*
* @author gokul.p
*/
@Transactional
public interface FileRepoRepository extends CommonCrudRepository<FileRepo, Long> {
    public Page<FileRepo> findByIsActive(boolean isActive, Pageable page);
}
