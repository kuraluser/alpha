/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.ListOfUllageReportResponse;
import com.cpdss.gateway.domain.filerepo.FileRepoGetResponse;
import com.cpdss.gateway.domain.filerepo.FileRepoReply;
import com.cpdss.gateway.entity.FileRepo;
import com.cpdss.gateway.service.FileRepoService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class CommonController {

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  private static final String CORRELATION_ID_HEADER = "correlationId";
  @Autowired LoadingPlanService loadingPlanService;
  @Autowired FileRepoService fileRepoService;
  @Autowired CacheManager cacheManager;

  /**
   * To import ullage report excel file
   *
   * @param headers
   * @param file
   * @return UploadTideDetailResponse
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/import/ullage-report-file",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ListOfUllageReportResponse importUllageReportFile(
      @RequestHeader HttpHeaders headers,
      @RequestParam(name = "file", required = true) MultipartFile file,
      @RequestParam(name = "infoId", required = true) Long infoId,
      @RequestParam(name = "isLoading", required = true) boolean isLoading,
      @RequestParam(name = "cargoNominationId", required = true) Long cargoNominationId,
      @RequestParam(name = "vesselId", required = true) Long vesselId,
      @RequestParam(name = "tanks", required = true) Object tanks)
      throws CommonRestException {
    try {
      return loadingPlanService.importUllageReportFile(
          file,
          tanks.toString(),
          infoId,
          cargoNominationId,
          headers.getFirst(CORRELATION_ID_HEADER),
          isLoading,
          vesselId);

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when upload ullage report details", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when upload ullage report details", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @GetMapping("/file-repo")
  public FileRepoGetResponse getFileRepoDetails(
      @RequestHeader HttpHeaders headers,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam(required = false, defaultValue = "0") int pageNo,
      @RequestParam(required = false) String sortBy,
      @RequestParam(required = false, defaultValue = "ASC") String orderBy,
      @RequestParam Map<String, String> params)
      throws CommonRestException {
    try {
      // Get filters
      List<String> filterKeys =
          Arrays.asList(
              "voyageNumber", "fileName", "fileType", "section", "category", "createdDate");

      Map<String, String> filterParams =
          params.entrySet().stream()
              .filter(e -> filterKeys.contains(e.getKey()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      return fileRepoService.getFileRepoDetails(
          pageSize,
          pageNo,
          sortBy,
          orderBy,
          filterParams,
          headers.getFirst(CORRELATION_ID_HEADER),
          filterKeys);

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when getting file repo details", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when getting file repo details", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To add file to file-repo
   *
   * @param headers
   * @param file
   * @return FileRepoResponse
   * @throws CommonRestException
   */
  @PostMapping(
      value = "/file-repo",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public FileRepoReply addFileToRepo(
      @RequestHeader HttpHeaders headers,
      @RequestParam(name = "file", required = true) MultipartFile file,
      @RequestParam(name = "voyageNo", required = true) String voyageNo,
      @RequestParam(name = "fileName", required = true) String fileName,
      @RequestParam(name = "fileType", required = true) String fileType,
      @RequestParam(name = "section", required = true) String section,
      @RequestParam(name = "category", required = true) String category)
      throws CommonRestException {
    try {
      return fileRepoService.addFileToRepo(
          file,
          voyageNo,
          fileName,
          fileType,
          section,
          category,
          headers.getFirst(CORRELATION_ID_HEADER),
          false);

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving file repo details", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving file repo details", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To delete file in file-repo
   *
   * @param headers
   * @param repoId
   * @return FileRepoResponse
   * @throws CommonRestException
   */
  @DeleteMapping("/file-repo/{repoId}")
  public FileRepoReply removeFromFileRepo(
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "repoId", required = true) Long repoId)
      throws CommonRestException {
    try {
      return fileRepoService.removeFromFileRepo(repoId, headers.getFirst(CORRELATION_ID_HEADER));

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving file repo details", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when saving file repo details", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To download file in file-repo
   *
   * @param headers
   * @param repoId
   * @return FileRepoResponse
   * @throws CommonRestException
   */
  @GetMapping(value = "/file-repo/{repoId}/download")
  public ResponseEntity<Resource> getFileRepoFile(
      @PathVariable @Min(value = 1, message = CommonErrorCodes.E_HTTP_BAD_REQUEST) Long repoId,
      @RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {

      FileRepo fileRepo = this.fileRepoService.getFileRepoDetailsById(repoId);
      if (null != fileRepo) {
        String filePath = fileRepo.getFilePath().replace("{{basePath}}", this.rootFolder);
        File file = new File(filePath);
        InputStreamResource inputStream = null;
        try {
          inputStream = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
          log.error("FileNotFoundException in file repo", e);
          throw new FileNotFoundException(e.getMessage());
        }

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(inputStream);
      }
      return ResponseEntity.badRequest().body(null);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when getLoadableStudyAttachment", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when get getLoadableStudyAttachment", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * To add file to file-repo
   *
   * @param headers
   * @param file
   * @return FileRepoResponse
   * @throws CommonRestException
   */
  @PutMapping(
      value = "/file-repo/{repoId}",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public FileRepoReply editFileRepo(
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "repoId", required = true) Long repoId,
      @RequestParam(name = "file", required = false) MultipartFile file,
      @RequestParam(name = "section", required = true) String section,
      @RequestParam(name = "category", required = true) String category,
      @RequestParam(name = "hasFileChanged", required = true, defaultValue = "true")
          Boolean hasFileChanged)
      throws CommonRestException {
    try {
      return fileRepoService.editFileRepo(
          repoId, file, section, category, hasFileChanged, headers.getFirst(CORRELATION_ID_HEADER));

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when editing file repo details", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception when editing file repo details", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * Delete api to clear the requested cache, this api is only for help the developers to clear the
   * cache easily. this is not part for the CPDSS Usecase.
   *
   * @param cacheName
   */
  @DeleteMapping("/clear-cache")
  public void clearCache(@RequestParam(name = "cacheName", required = true) String cacheName) {

    if (cacheName.equalsIgnoreCase("all")) {

      for (String name : cacheManager.getCacheNames()) {
        cacheManager.getCache(name).clear(); // clear cache by name
      }
    } else {
      Collection<String> cacheNames = cacheManager.getCacheNames();
      cacheNames.forEach(item -> System.out.println(item));
      if (cacheManager.getCache(cacheName) != null) {
        // Cache cache = cacheManager.getCache("vesselDetails");
        cacheManager.getCache(cacheName).clear(); // clear cache by name
      }
    }
  }
}
