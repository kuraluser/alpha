/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.EnvoyWriterServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.gateway.entity.FileRepo;
import com.cpdss.gateway.repository.FileRepoRepository;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {FileRepoService.class})
@TestPropertySource(properties = "cpdss.communication.enable = false")
public class FileRepoServiceTest {

  @Autowired FileRepoService fileRepoService;

  @MockBean FileRepoRepository fileRepoRepository;

  @MockBean private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  @Value("${cpdss.communication.enable}")
  private Boolean enableCommunication;

  @Test
  void testGetFileRepoDetails() {
    int pageSize = 1;
    int pageNo = 1;
    String sortBy = "1";
    String orderBy = "1";
    Map<String, String> filterParams = new HashMap<>();
    filterParams.put("createdDate", "10-Dec-1999");
    String correlationId = "1";
    List<String> filterKeys = new ArrayList<>();
    filterKeys.add("createdDate");
    Mockito.when(
            fileRepoRepository.findAll(
                (Specification<FileRepo>) Mockito.any(), Mockito.any(Pageable.class)))
        .thenReturn(getPFR());
    try {
      var response =
          this.fileRepoService.getFileRepoDetails(
              pageSize, pageNo, sortBy, orderBy, filterParams, correlationId, filterKeys);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Page<FileRepo> getPFR() {
    FileRepo fileRepo = new FileRepo();
    fileRepo.setId(1L);
    fileRepo.setCreatedBy("1");
    fileRepo.setCategory("1");
    fileRepo.setCreatedDate(LocalDate.now());
    fileRepo.setFileName("1");
    fileRepo.setFileType("1");
    fileRepo.setIsActive(true);
    fileRepo.setLastModifiedBy("1");
    fileRepo.setIsTransferred(true);
    fileRepo.setSection("1");
    fileRepo.setVoyageNumber("1");
    fileRepo.setIsSystemGenerated(true);
    return new PageImpl<>(Collections.singletonList(fileRepo));
  }

  @Test
  void testGetFileRepoDetails1() {
    int pageSize = 1;
    int pageNo = 1;
    String sortBy = "1";
    String orderBy = "1";
    Map<String, String> filterParams = new HashMap<>();
    filterParams.put("heloo", "10-Dec-1999");
    String correlationId = "1";
    List<String> filterKeys = new ArrayList<>();
    filterKeys.add("heloo");
    Mockito.when(
            fileRepoRepository.findAll(
                (Specification<FileRepo>) Mockito.any(), Mockito.any(Pageable.class)))
        .thenReturn(getPFR());
    try {
      var response =
          this.fileRepoService.getFileRepoDetails(
              pageSize, pageNo, sortBy, orderBy, filterParams, correlationId, filterKeys);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  //    @Test
  //      void testAddFileToRepo() {
  //        MultipartFile file = new MultipartFile() {
  //            @Override
  //            public String getName() {
  //                return null;
  //            }
  //
  //            @Override
  //            public String getOriginalFilename() {
  //                return "docx";
  //            }
  //
  //            @Override
  //            public String getContentType() {
  //                return null;
  //            }
  //
  //            @Override
  //            public boolean isEmpty() {
  //                return false;
  //            }
  //
  //            @Override
  //            public long getSize() {
  //                return 1L;
  //            }
  //
  //            @Override
  //            public byte[] getBytes() throws IOException {
  //                return new byte[0];
  //            }
  //
  //            @Override
  //            public InputStream getInputStream() throws IOException {
  //                return null;
  //            }
  //
  //            @Override
  //            public void transferTo(File dest) throws IOException, IllegalStateException {
  //
  //            }
  //        };
  //        String voyageNo = "1";
  //        String fileNameX = "1";
  //        String filePath = "1";
  //        String section = "1";
  //        String category = "1";
  //        String correlationId ="1";
  //        Boolean isSystemGenerated = false;
  //        try {
  //            var response =
  // this.fileRepoService.addFileToRepo(file,voyageNo,fileNameX,filePath,section,category,correlationId,isSystemGenerated);
  //            File file1 = new File(this.rootFolder);
  //            deleteFolder(file1);
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }

  static void deleteFolder(File file) {
    for (File subFile : file.listFiles()) {
      if (subFile.isDirectory()) {
        deleteFolder(subFile);
      } else {
        subFile.delete();
      }
    }
    file.delete();
  }

  @Test
  void testRemoveFromFileRepo() {
    Long repoId = 1L;
    String correlationId = "1";
    Mockito.when(this.fileRepoRepository.findById(Mockito.anyLong())).thenReturn(getOFR());
    try {
      var response = this.fileRepoService.removeFromFileRepo(repoId, correlationId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<FileRepo> getOFR() {
    FileRepo fileRepo = new FileRepo();
    fileRepo.setFileName("hey");
    return Optional.of(fileRepo);
  }

  @Test
  void testRemoveFromFileRepoException() {
    Long repoId = 1L;
    String correlationId = "1";
    Mockito.when(this.fileRepoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    try {
      var response = this.fileRepoService.removeFromFileRepo(repoId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetFileRepoDetailsById() {
    Long repoId = 1L;
    Mockito.when(this.fileRepoRepository.findById(Mockito.anyLong())).thenReturn(getOFR());
    try {
      var response = this.fileRepoService.getFileRepoDetailsById(repoId);
      assertEquals("hey", response.getFileName());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetFileRepoDetailsByIdException() {
    Long repoId = 1L;
    Mockito.when(this.fileRepoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    try {
      var response = this.fileRepoService.getFileRepoDetailsById(repoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }
}
