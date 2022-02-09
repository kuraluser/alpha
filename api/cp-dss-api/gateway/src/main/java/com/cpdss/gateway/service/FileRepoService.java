/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.constants.FileRepoConstants.FileRepoSection;
import com.cpdss.common.domain.FileRepoReply;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.EnvoyWriter;
import com.cpdss.common.generated.EnvoyWriterServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.gateway.domain.filerepo.FileRepoGetResponse;
import com.cpdss.gateway.domain.filerepo.FileRepoResponse;
import com.cpdss.gateway.domain.filerepo.FileRepoSpecification;
import com.cpdss.gateway.domain.filerepo.SearchCriteria;
import com.cpdss.gateway.entity.FileRepo;
import com.cpdss.gateway.repository.FileRepoRepository;
import com.cpdss.gateway.service.communication.models.FileData;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
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

@Slf4j
@Service
public class FileRepoService {

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  @Value("${cpdss.communication.enable}")
  private Boolean enableCommunication;

  private static final int ATTACHEMENT_MAX_SIZE = 1 * 1024 * 1024;
  private static final List<String> ATTACHMENT_ALLOWED_EXTENSIONS =
      Arrays.asList("docx", "doc", "pdf", "txt", "jpg", "png", "msg", "eml", "xlsx", "xls", "csv");
  public static final String DATE_FORMAT = "dd-MM-yyyy";

  public static final String SUCCESS = "SUCCESS";

  @Autowired FileRepoRepository fileRepoRepository;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  public FileRepoGetResponse getFileRepoDetails(
      int pageSize,
      int pageNo,
      String sortBy,
      String orderBy,
      Map<String, String> filterParams,
      String correlationId,
      List<String> filterKeys)
      throws GenericServiceException {
    FileRepoGetResponse response = new FileRepoGetResponse();
    Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
    Specification<FileRepo> specification = this.getFileRepoSpecification(filterParams, filterKeys);
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

  private Specification<FileRepo> getFileRepoSpecification(
      Map<String, String> filterParams, List<String> filterKeys) {
    Specification<FileRepo> specification =
        new FileRepoSpecification(new SearchCriteria("isActive", "EQUALS", Boolean.TRUE));
    for (int i = 0; i < filterKeys.size(); i++) {
      String key = filterKeys.get(i);
      if (key.equalsIgnoreCase("createdDate") && null != filterParams.get(key)) {
        LocalDate date =
            LocalDate.parse(filterParams.get(key), DateTimeFormatter.ofPattern(DATE_FORMAT));
        specification =
            specification.and(new FileRepoSpecification(new SearchCriteria(key, "EQUALS", date)));
      } else if (key.equalsIgnoreCase("vesselId") && null != filterParams.get(key)) {
        Long vesselId = Long.valueOf(filterParams.get(key));
        specification =
            specification.and(
                new FileRepoSpecification(new SearchCriteria("vesselXId", "EQUALS", vesselId)));
      } else if (null != filterParams.get(key)) {
        specification =
            specification.and(
                new FileRepoSpecification(
                    new SearchCriteria(
                        key, "LIKE", "%" + filterParams.get(key).toString().toLowerCase() + "%")));
      }
    }
    ;
    return specification;
  }

  /**
   * Method to add file to file repo
   *
   * @param file file object
   * @param voyageNo voyage no value
   * @param fileName fileName value
   * @param filePath faile path
   * @param section section name
   * @param category category value
   * @param correlationId tracking id
   * @param vesselId vessel id value
   * @param isSystemGenerated system generated flag
   * @return FileRepoReply object
   * @throws GenericServiceException Exception on failure
   */
  public FileRepoReply addFileToRepo(
      MultipartFile file,
      String voyageNo,
      String fileName,
      String filePath,
      FileRepoSection section,
      String category,
      String correlationId,
      Long vesselId,
      Boolean isSystemGenerated)
      throws GenericServiceException {

    return this.validateAndAddFile(
        file,
        new FileRepo(),
        voyageNo,
        section,
        category,
        filePath,
        fileName,
        correlationId,
        vesselId,
        isSystemGenerated);
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

  /**
   * Method to edit file repo
   *
   * @param repoId repo Id value
   * @param file file object
   * @param section section value
   * @param category category value
   * @param hasFileChanged file changed flag
   * @param correlationId tracking id
   * @param vesselId vessel id value
   * @return FileRepoReply object
   * @throws GenericServiceException exception on failure
   */
  public FileRepoReply editFileRepo(
      Long repoId,
      MultipartFile file,
      FileRepoSection section,
      String category,
      Boolean hasFileChanged,
      String correlationId,
      Long vesselId)
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
                file,
                repo,
                repo.getVoyageNumber(),
                section,
                category,
                null,
                null,
                correlationId,
                vesselId,
                false);
      }
    } else {
      repo.setCategory(category);
      repo.setSection(section.getSection());
      repo.setIsTransferred(false);
      repo = this.fileRepoRepository.save(repo);
      reply.setId(repo.getId());
      reply.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    }
    return reply;
  }

  /**
   * Method to validate and add file
   *
   * @param file file object
   * @param repo repo object
   * @param voyageNo voyage number value
   * @param section section name
   * @param category category value
   * @param filePath filepath value
   * @param originalFileName actual file name
   * @param correlationId tracking id
   * @param vesselId vessel id value
   * @param isSystemGenerated system generated flag
   * @return FileRepoReply object
   * @throws GenericServiceException Exception on failure
   */
  private FileRepoReply validateAndAddFile(
      MultipartFile file,
      FileRepo repo,
      String voyageNo,
      FileRepoSection section,
      String category,
      String filePath,
      String originalFileName,
      String correlationId,
      Long vesselId,
      Boolean isSystemGenerated)
      throws GenericServiceException {
    return validateAndAddFile(
        file,
        repo,
        voyageNo,
        section.getSection(),
        category,
        filePath,
        originalFileName,
        correlationId,
        vesselId,
        isSystemGenerated);
  }

  private FileRepoReply validateAndAddFile(
      MultipartFile file,
      FileRepo repo,
      String voyageNo,
      String section,
      String category,
      String filePath,
      String originalFileName,
      String correlationId,
      Long vesselId,
      Boolean isSystemGenerated)
      throws GenericServiceException {
    FileRepoReply reply = new FileRepoReply();
    if (file != null) {
      originalFileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
    }
    String extension =
        originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
    if (file != null) {
      if (file.getSize() > ATTACHEMENT_MAX_SIZE) {
        throw new GenericServiceException(
            "loadable study attachment size exceeds maximum allowed size",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      if (!ATTACHMENT_ALLOWED_EXTENSIONS.contains(extension)) {
        throw new GenericServiceException(
            "unsupported file type",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    }
    try {
      String folderLocation;
      if (!isSystemGenerated) {
        folderLocation = "/file-repo/" + voyageNo + "/";
      } else {
        folderLocation = filePath;
      }
      Path path = null;
      if (file != null) {
        Files.createDirectories(Paths.get(this.rootFolder + folderLocation));
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        filePath = folderLocation + fileName + UUID.randomUUID().toString() + '.' + extension;
        path = Paths.get(this.rootFolder + filePath);
        Files.createFile(path);
        Files.write(
            path, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      }
      repo.setVesselXId(vesselId);
      repo.setVoyageNumber(voyageNo);
      repo.setFileName(originalFileName);
      repo.setFileType(extension);
      repo.setFilePath("{{basePath}}" + filePath);
      repo.setCategory(category);
      repo.setSection(section);
      repo.setIsActive(true);
      repo.setIsTransferred(false);
      repo.setIsSystemGenerated(isSystemGenerated);
      repo = this.fileRepoRepository.save(repo);
      reply.setId(repo.getId());
      reply.setFilePath(repo.getFilePath());
      reply.setResponseStatus(
          new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
      if (enableCommunication) {
        repo.setIsTransferred(false);
        FileData fileData = new FileData();
        fileData.setDetails(getRowAsJsonById(repo.getId()));
        byte[] bytes = Files.readAllBytes(path);
        String fileDataToString = new String(bytes, StandardCharsets.UTF_16);
        fileData.setData(fileDataToString);
        log.info("File Data object :{}", fileData);
        writeToEnvoy(
            new Gson().toJson(fileData),
            MessageTypes.FILE_SHAREING.getMessageType(),
            repo.getVesselXId());
      }
      return reply;
    } catch (IOException e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "Error while creating directory",
          CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  private void writeToEnvoy(String json, String messageType, Long vesselId) {
    log.info("Json Array in File repo service: " + json);
    EnvoyWriter.WriterReply ewReply = passRequestPayloadToEnvoyWriter(json, messageType, vesselId);
    if (SUCCESS.equals(ewReply.getResponseStatus().getStatus())) {
      log.info("------- Envoy writer has called successfully " + ewReply);
    } else {
      log.info("----- Failed to write in writeToEnvoy ----- ");
    }
  }

  public EnvoyWriter.WriterReply passRequestPayloadToEnvoyWriter(
      String requestJson, String messageType, Long vesselId) {
    EnvoyWriter.EnvoyWriterRequest.Builder writerRequest =
        EnvoyWriter.EnvoyWriterRequest.newBuilder();
    try {
      VesselInfo.VesselDetail vesselReply = this.getVesselDetailsForEnvoy(vesselId);
      writerRequest.setJsonPayload(requestJson);
      writerRequest.setClientId(vesselReply.getName());
      writerRequest.setMessageType(messageType);
      writerRequest.setImoNumber(vesselReply.getImoNumber());
    } catch (GenericServiceException e) {
      log.error("Error in passRequestPayloadToEnvoyWriter", e);
    }
    return this.envoyWriterService.getCommunicationServer(writerRequest.build());
  }

  private String getRowAsJsonById(Long id) {
    return fileRepoRepository.getRowAsJsonById(id);
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

  // region get vessel details
  public VesselInfo.VesselDetail getVesselDetailsForEnvoy(Long vesselId)
      throws GenericServiceException {
    VesselInfo.VesselIdRequest replyBuilder =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(vesselId).build();
    VesselInfo.VesselIdResponse vesselResponse =
        this.vesselInfoGrpcService.getVesselInfoByVesselId(replyBuilder);
    if (!SUCCESS.equalsIgnoreCase(vesselResponse.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling vessel service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return vesselResponse.getVesselDetail();
  }
  // endregion
}
