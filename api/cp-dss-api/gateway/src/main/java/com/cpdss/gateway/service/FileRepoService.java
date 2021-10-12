package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.filerepo.FileRepoGetResponse;
import com.cpdss.gateway.domain.filerepo.FileRepoReply;
import com.cpdss.gateway.entity.FileRepo;
import com.cpdss.gateway.repository.FileRepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileRepoService {

    @Value("${gateway.attachement.rootFolder}")
    private String rootFolder;
    private static final int ATTACHEMENT_MAX_SIZE = 1 * 1024 * 1024;
    private static final List<String> ATTACHMENT_ALLOWED_EXTENSIONS =
            Arrays.asList("docx", "pdf", "txt", "jpg", "png", "msg", "eml");

    @Autowired FileRepoRepository fileRepoRepository;

    public FileRepoGetResponse getFileRepoDetails(int pageSize, int pageNo, String sortBy, String orderBy, Map<String, String> filterParams,  String correlationId)
            throws GenericServiceException {
        FileRepoGetResponse response = new FileRepoGetResponse();
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);
        Page<FileRepo> fileRepoPage = this.fileRepoRepository.findByIsActive(true, pageRequest);
        log.info("Retrieved file repos : {}",fileRepoPage.toList().size());
//         filtering each column
        List<FileRepo> list = this.getFilteredValues(filterParams, fileRepoPage.toList());
        log.info("Retrieved filtered repos : {}",list.size());
        Page<FileRepo> page = new PageImpl<FileRepo>(list, PageRequest.of(pageNo, pageSize), list.size());
        response.setFileRepos(list);
        response.setTotalElements(fileRepoPage.getTotalElements());
        response.setResponseStatus(new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
        return response;
    }

    public FileRepoReply addFileToRepo(MultipartFile file, String voyageNo, String fileNameX, String fileType, String section, String category, String correlationId) throws GenericServiceException {
        FileRepo repo = new FileRepo();
        FileRepoReply reply = this.validateAndAddFile(file, repo, voyageNo, section, category, correlationId);
        return reply;
    }

    public FileRepoReply removeFromFileRepo(Long repoId, String correlationId) throws GenericServiceException {
        FileRepoReply reply = new FileRepoReply();
        Optional<FileRepo> repo = this.fileRepoRepository.findById(repoId);
        if(repo.isEmpty()){
            throw new GenericServiceException(
                    "Record with provided ID doesn't exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
        } else {
            FileRepo entity = repo.get();
            entity.setIsActive(false);
            this.fileRepoRepository.save(entity);
            reply.setId(repoId);
            reply.setResponseStatus(new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
        }
        return reply;
    }

    public FileRepo getFileRepoDetailsById(Long repoId) throws GenericServiceException {
        Optional<FileRepo> repo = this.fileRepoRepository.findById(repoId);
        if(repo.isEmpty()){
            throw new GenericServiceException(
                    "Record with provided ID doesn't exist",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
        } else {
            FileRepo entity = repo.get();
            return entity;
        }
    }

    private List<FileRepo> getFilteredValues(
            Map<String, String> filterParams, List<FileRepo> repoList) {
        return repoList.stream()
                .filter(
                        repo -> {
                            Boolean status = true;
                            if (null != filterParams.get("voyageNo")) {
                                status =
                                        status
                                                && repo
                                                .getVoyageNumber()
                                                .toLowerCase()
                                                .contains(filterParams.get("voyageNo").toLowerCase());
                            }
                            if (null != filterParams.get("fileName")) {
                                status =
                                        status
                                                && repo
                                                .getFileName()
                                                .toLowerCase()
                                                .contains(filterParams.get("fileName").toLowerCase());
                            }
                            if (null != filterParams.get("fileType")) {
                                status =
                                        status
                                                && repo
                                                .getFileType()
                                                .toLowerCase()
                                                .contains(filterParams.get("fileType").toLowerCase());
                            }
                            return status;
                        })
                .collect(Collectors.toList());
    }

    public FileRepoReply editFileRepo(Long repoId, MultipartFile file, String section, String category, Boolean hasFileChanged, String correlationId) throws GenericServiceException{
        FileRepoReply reply = new FileRepoReply();
        FileRepo repo = this.getFileRepoDetailsById(repoId);
        if(hasFileChanged){
            String filePath = repo.getFilePath().replace("{{basePath}}",this.rootFolder);
            File oldFile = new File(filePath);
            if(!oldFile.delete()){
                throw new GenericServiceException(
                        "Error while deleting file",
                        CommonErrorCodes.E_HTTP_BAD_REQUEST,
                        HttpStatusCode.BAD_REQUEST);
            }
            reply = this.validateAndAddFile(file, repo, repo.getVoyageNumber(), section, category, correlationId);
        }
        return reply;
    }

    private FileRepoReply validateAndAddFile(MultipartFile file, FileRepo repo, String voyageNo, String section, String category, String correlationId ) throws GenericServiceException{
        FileRepoReply reply = new FileRepoReply();
        String originalFileName =
                file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        if (file.getSize() > ATTACHEMENT_MAX_SIZE) {
            throw new GenericServiceException(
                    "loadable study attachment size exceeds maximum allowed size",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
        }
        String extension =
                originalFileName
                        .substring(originalFileName.lastIndexOf(".") + 1)
                        .toLowerCase();
        if (!ATTACHMENT_ALLOWED_EXTENSIONS.contains(extension)) {
            throw new GenericServiceException(
                    "unsupported file type",
                    CommonErrorCodes.E_HTTP_BAD_REQUEST,
                    HttpStatusCode.BAD_REQUEST);
        }
        String folderLocation = "/file-repo/" + voyageNo + "/";
        try {
            Files.createDirectories(Paths.get(this.rootFolder + folderLocation));

            String fileName =
                    originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String filePath =
                    folderLocation + fileName + '.' + extension;
            Path path = Paths.get(this.rootFolder + filePath);
            Files.createFile(path);
            Files.write(path, file.getBytes());

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
            reply.setResponseStatus(new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
            return reply;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GenericServiceException(
                    "Error while creating directory",
                    CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
                    HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
