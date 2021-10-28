/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.filerepo.*;
import com.cpdss.gateway.entity.FileRepo;
import com.cpdss.gateway.repository.FileRepoRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static java.nio.file.StandardOpenOption.WRITE;

@Slf4j
@Service
public class FileRepoService {

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  private static final int ATTACHEMENT_MAX_SIZE = 1 * 1024 * 1024;
  private static final List<String> ATTACHMENT_ALLOWED_EXTENSIONS =
      Arrays.asList("docx", "pdf", "txt", "csv", "xlsx");
  public static final String DATE_FORMAT = "dd-MMM-yyyy";

  @Autowired FileRepoRepository fileRepoRepository;

  public FileRepoGetResponse getFileRepoDetails(
          int pageSize,
          int pageNo,
          String sortBy,
          String orderBy,
          Map<String, String> filterParams,
          String correlationId, List<String> filterKeys)
      throws GenericServiceException {
    FileRepoGetResponse response = new FileRepoGetResponse();
    Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC,"id"));
    Specification<FileRepo> specification =this.getFileRepoSpecification(filterParams, filterKeys);
    Page<FileRepo> fileRepoPage = this.fileRepoRepository.findAll(specification, pageRequest);
    log.info("Retrieved file repos : {}", fileRepoPage.toList().size());
    List<FileRepo> list = fileRepoPage.toList();
//    List<FileRepo> list = this.getFilteredValues(filterParams, fileRepoPage.toList());
    log.info("Retrieved filtered repos : {}", list.size());
    List<FileRepoResponse> formattedList = this.formatFileRepos(list);
    response.setFileRepos(formattedList);
    response.setTotalElements(fileRepoPage.getTotalElements());
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  private Specification<FileRepo> getFileRepoSpecification(Map<String, String> filterParams, List<String> filterKeys) {
    Specification<FileRepo> specification =
                    new FileRepoSpecification(
                            new SearchCriteria("isActive", "EQUALS", Boolean.TRUE));
    for(int i = 0;i < filterKeys.size(); i ++){
     String key = filterKeys.get(i);
     if(key.equalsIgnoreCase("createdDate") && null != filterParams.get(key)) {
       LocalDate date = LocalDate.parse(filterParams.get(key),DateTimeFormatter.ofPattern(DATE_FORMAT));
       specification =
               specification.and(
                       new FileRepoSpecification(
                               new SearchCriteria(key, "EQUALS", date)));
     } else if (null != filterParams.get(key)) {
        specification =
                specification.and(
                        new FileRepoSpecification(
                                new SearchCriteria(key, "LIKE", "%" + filterParams.get(key).toString().toLowerCase() + "%")));
      }
    };
    return specification;
  }

  public FileRepoReply addFileToRepo(
      MultipartFile file,
      String voyageNo,
      String fileNameX,
      String fileType,
      String section,
      String category,
      String correlationId)
      throws GenericServiceException {
    FileRepo repo = new FileRepo();
    FileRepoReply reply =
        this.validateAndAddFile(file, repo, voyageNo, section, category, null, fileNameX, correlationId);
    return reply;
  }

  public FileRepoReply removeFromFileRepo(Long repoId, String correlationId)
      throws GenericServiceException {
    FileRepoReply reply = new FileRepoReply();
    Optional<FileRepo> repo = this.fileRepoRepository.findById(repoId);
    if (repo.isEmpty()) {
      throw new GenericServiceException(
          "Record with provided ID doesn't exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } else {
      FileRepo entity = repo.get();
      entity.setIsActive(false);
      this.fileRepoRepository.save(entity);
      reply.setId(repoId);
      reply.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    }
    return reply;
  }

  public FileRepo getFileRepoDetailsById(Long repoId) throws GenericServiceException {
    Optional<FileRepo> repo = this.fileRepoRepository.findById(repoId);
    if (repo.isEmpty()) {
      throw new GenericServiceException(
          "Record with provided ID doesn't exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } else {
      FileRepo entity = repo.get();
      return entity;
    }
  }

  public FileRepoReply editFileRepo(
      Long repoId,
      MultipartFile file,
      String section,
      String category,
      Boolean hasFileChanged,
      String correlationId)
      throws GenericServiceException {
    FileRepoReply reply = new FileRepoReply();
    FileRepo repo = this.getFileRepoDetailsById(repoId);
    if (hasFileChanged) {
      String filePath = repo.getFilePath().replace("{{basePath}}", this.rootFolder);
      File oldFile = new File(filePath);
      if (!oldFile.delete()) {
        throw new GenericServiceException(
            "Error while deleting file",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      } else {
        reply =
            this.validateAndAddFile(
                file, repo, repo.getVoyageNumber(), section, category, null, null, correlationId);
      }
    } else {
      repo.setCategory(category);
      repo.setSection(section);
      repo.setIsTransferred(false);
      repo = this.fileRepoRepository.save(repo);
      reply.setId(repo.getId());
      reply.setResponseStatus(
              new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    }
    return reply;
  }

  private FileRepoReply validateAndAddFile(
      MultipartFile file,
      FileRepo repo,
      String voyageNo,
      String section,
      String category,
      String filePath,
      String originalFileName,
      String correlationId)
      throws GenericServiceException {
    FileRepoReply reply = new FileRepoReply();
    if(file != null){
      originalFileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
    }
    String extension =
            originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
    if(file != null){
      if (file.getSize() > ATTACHEMENT_MAX_SIZE) {
        throw new GenericServiceException(
            "loadable study attachment size exceeds maximum allowed size",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      if (!ATTACHMENT_ALLOWED_EXTENSIONS.contains(extension)) {
        throw new GenericServiceException(
            "unsupported file type", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
      }
    }
    try {
      if(filePath == null && file != null){
        String folderLocation = "/file-repo/" + voyageNo + "/";
        System.out.println(this.rootFolder + folderLocation);
        Files.createDirectories(Paths.get(this.rootFolder + folderLocation));
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        filePath = folderLocation + fileName + '.' + extension;
        Path path = Paths.get(this.rootFolder + filePath);
        Files.createFile(path);
        Files.write(path, file.getBytes(),StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      }

      repo.setVoyageNumber(voyageNo);
      repo.setFileName(originalFileName);
      repo.setFileType(extension);
      repo.setFilePath("{{basePath}}" + filePath);
      repo.setCategory(category);
      repo.setSection(section);
      repo.setIsActive(true);
      repo.setIsTransferred(false);
      repo = this.fileRepoRepository.save(repo);
      reply.setId(repo.getId());
      reply.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
      return reply;
    } catch (IOException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Error while creating directory",
          CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  private List<FileRepoResponse> formatFileRepos(List<FileRepo> list) {
    List<FileRepoResponse> formattedList = new ArrayList<>();
    if (list.size() > 0) {
      list.forEach(
          fileRepo -> {
            FileRepoResponse formattedRepo = new FileRepoResponse();
            formattedRepo.setId(fileRepo.getId());
            formattedRepo.setCreatedBy(fileRepo.getCreatedBy());
            formattedRepo.setCategory(fileRepo.getCategory());
            formattedRepo.setCreatedDate(
                fileRepo.getCreatedDate() != null
                    ? fileRepo.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
                    : "");
            formattedRepo.setFileName(fileRepo.getFileName());
            formattedRepo.setFileType(fileRepo.getFileType());
            formattedRepo.setIsActive(fileRepo.getIsActive());
            formattedRepo.setLastModifiedBy(fileRepo.getLastModifiedBy());
            formattedRepo.setIsTransferred(fileRepo.getIsTransferred());
            formattedRepo.setSection(fileRepo.getSection());
            formattedRepo.setVoyageNumber(fileRepo.getVoyageNumber());
            formattedRepo.setIsSystemGenerated(fileRepo.getIsSystemGenerated());
            formattedList.add(formattedRepo);
          });
    }
    return formattedList;
  }
}
