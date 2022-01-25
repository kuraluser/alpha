/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static com.cpdss.common.constants.FileRepoConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.domain.FileRepoReply;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.ListOfUllageReportResponse;
import com.cpdss.gateway.domain.filerepo.FileRepoGetResponse;
import com.cpdss.gateway.security.ship.*;
import com.cpdss.gateway.service.FileRepoService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@MockitoSettings
@WebMvcTest(controllers = CommonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
public class CommonControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean LoadingPlanService loadingPlanService;
  @MockBean FileRepoService fileRepoService;
  @MockBean CacheManager cacheManager;
  @MockBean ShipJwtService shipJwtService;
  @MockBean ShipAuthenticationProvider shipAuthenticationProvider;
  @MockBean ShipUserAuthenticationProvider shipUserAuthenticationProvider;
  @MockBean ShipUserDetailService shipUserDetailService;
  @MockBean ShipTokenExtractor shipTokenExtractor;
  @MockBean AuthenticationFailureHandler authenticationFailureHandler;
  @Autowired CommonController commonController;
  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";
  private static final String GET_ULLAGE_REPORT_FILES = "/import/ullage-report-file";
  private static final String GET_FILE_REPO = "/file-repo";
  private static final String DELETE_FILE_REPO = "/file-repo/{repoId}";
  private static final String GET_FILE_REPO_FILE = "/file-repo/{repoId}/download";
  private static final String GET_CLEAR_CACHE = "/clear-cache";
  private static final String GET_ULLAGE_REPORT_FILES_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_ULLAGE_REPORT_FILES;
  private static final String GET_ULLAGE_REPORT_FILES_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_ULLAGE_REPORT_FILES;
  private static final String GET_FILE_REPO_CLOUD_API_URL = CLOUD_API_URL_PREFIX + GET_FILE_REPO;
  private static final String GET_FILE_REPO_SHIP_API_URL = SHIP_API_URL_PREFIX + GET_FILE_REPO;
  private static final String DELETE_FILE_REPO_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DELETE_FILE_REPO;
  private static final String DELETE_FILE_REPO_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DELETE_FILE_REPO;
  private static final String GET_FILE_REPO_FILE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_FILE_REPO_FILE;
  private static final String GET_FILE_REPO_FILE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_FILE_REPO_FILE;
  private static final String GET_CLEAR_CACHE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_CLEAR_CACHE;
  private static final String GET_CLEAR_CACHE_SHIP_API_URL = SHIP_API_URL_PREFIX + GET_CLEAR_CACHE;

  @ValueSource(
      strings = {GET_ULLAGE_REPORT_FILES_CLOUD_API_URL, GET_ULLAGE_REPORT_FILES_SHIP_API_URL})
  @ParameterizedTest
  void testImportUllageReportFile(String url) throws Exception {
    Mockito.when(
            loadingPlanService.importUllageReportFile(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.anyLong()))
        .thenReturn(new ListOfUllageReportResponse());
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url)
                .file(firstFile)
                .param("infoId", "100000190")
                .param("isLoading", "true")
                .param("cargoNominationId", "200001242")
                .param("vesselId", "1")
                .param("tanks", "[{\"tankId\":25580,\"shortName\":\"1C\"}]")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {GET_ULLAGE_REPORT_FILES_CLOUD_API_URL, GET_ULLAGE_REPORT_FILES_SHIP_API_URL})
  @ParameterizedTest
  void testImportUllageReportFileServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanService.importUllageReportFile(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.anyLong()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url)
                .file(firstFile)
                .param("infoId", "100000190")
                .param("isLoading", "true")
                .param("cargoNominationId", "200001242")
                .param("vesselId", "1")
                .param("tanks", "[{\"tankId\":25580,\"shortName\":\"1C\"}]")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {GET_ULLAGE_REPORT_FILES_CLOUD_API_URL, GET_ULLAGE_REPORT_FILES_SHIP_API_URL})
  @ParameterizedTest
  void testImportUllageReportFileRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingPlanService.importUllageReportFile(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url)
                .file(firstFile)
                .param("infoId", "100000190")
                .param("isLoading", "true")
                .param("cargoNominationId", "200001242")
                .param("vesselId", "1")
                .param("tanks", "[{\"tankId\":25580,\"shortName\":\"1C\"}]")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_FILE_REPO_CLOUD_API_URL, GET_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testGetFileRepoDetails(String url) throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("voyageNumber", "1");
    Mockito.when(
            fileRepoService.getFileRepoDetails(
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyMap(),
                Mockito.anyString(),
                Mockito.anyList()))
        .thenReturn(new FileRepoGetResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  //  @ValueSource(strings = {GET_FILE_REPO_CLOUD_API_URL, GET_FILE_REPO_SHIP_API_URL})
  //  @ParameterizedTest
  //  void testGetFileRepoDetailsServiceException(String url) throws Exception {
  //
  // Mockito.when(fileRepoService.getFileRepoDetails(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString(),Mockito.anyMap()
  //                    ,Mockito.anyString(),Mockito.anyList()))
  //            .thenThrow(
  //                    new GenericServiceException(
  //                            "service exception",
  //                            CommonErrorCodes.E_GEN_INTERNAL_ERR,
  //                            HttpStatusCode.INTERNAL_SERVER_ERROR));
  //   //
  // Mockito.when(commonController.getFileRepoDetails(Mockito.any(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString(),Mockito.anyMap())).thenCallRealMethod();
  // //   ReflectionTestUtils.setField(
  //    //        commonController, "fileRepoService", fileRepoService);
  //    this.mockMvc
  //            .perform(
  //                    MockMvcRequestBuilders.get(url)
  //                            .param("params",null)
  //                            .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                            .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                            .accept(MediaType.APPLICATION_JSON_VALUE))
  //            .andExpect(status().isInternalServerError());
  //  }

  //  @ValueSource(strings = {GET_FILE_REPO_CLOUD_API_URL, GET_FILE_REPO_SHIP_API_URL})
  //  @ParameterizedTest
  //  void testGetFileRepoDetailsRuntimeException(String url) throws Exception {
  //
  // Mockito.when(fileRepoService.getFileRepoDetails(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString(),Mockito.anyMap(),Mockito.anyString(),Mockito.anyList()))
  //            .thenThrow(RuntimeException.class);
  //
  // Mockito.when(commonController.getFileRepoDetails(Mockito.any(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString(),Mockito.anyMap()))
  //            .thenCallRealMethod();
  //    ReflectionTestUtils.setField(commonController,"fileRepoService",fileRepoService);
  //    this.mockMvc
  //            .perform(
  //                    MockMvcRequestBuilders.get(url)
  //                            .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                            .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                            .accept(MediaType.APPLICATION_JSON_VALUE))
  //            .andExpect(status().isInternalServerError());
  //  }

  @ValueSource(strings = {GET_FILE_REPO_CLOUD_API_URL, GET_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testAddFileToRepo(String url) throws Exception {
    Mockito.when(
            fileRepoService.addFileToRepo(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(FileRepoSection.class),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong(),
                Mockito.anyBoolean()))
        .thenReturn(new FileRepoReply());
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url)
                .file("file", firstFile.getBytes())
                .param("voyageNo", "100000190")
                .param("fileName", "234")
                .param("fileType", "200001242")
                .param("section", "Loadable Study")
                .param("category", "2123")
                .param("vesselId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GET_FILE_REPO_CLOUD_API_URL, GET_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testAddFileToRepoServiceException(String url) throws Exception {
    Mockito.when(
            fileRepoService.addFileToRepo(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(FileRepoSection.class),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong(),
                Mockito.anyBoolean()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url)
                .file("file", firstFile.getBytes())
                .param("voyageNo", "100000190")
                .param("fileName", "234")
                .param("fileType", "200001242")
                .param("section", "Loadable Study")
                .param("category", "2123")
                .param("vesselId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_FILE_REPO_CLOUD_API_URL, GET_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testAddFileToRepoRuntimeException(String url) throws Exception {
    Mockito.when(
            fileRepoService.addFileToRepo(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(FileRepoSection.class),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong(),
                Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url)
                .file("file", firstFile.getBytes())
                .param("voyageNo", "100000190")
                .param("fileName", "234")
                .param("fileType", "200001242")
                .param("section", "Loadable Study")
                .param("category", "2123")
                .param("vesselId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DELETE_FILE_REPO_CLOUD_API_URL, DELETE_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testRemoveFromFileRepo(String url) throws Exception {
    Mockito.when(fileRepoService.removeFromFileRepo(Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new FileRepoReply());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DELETE_FILE_REPO_CLOUD_API_URL, DELETE_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testRemoveFromFileRepoServiceException(String url) throws Exception {
    Mockito.when(fileRepoService.removeFromFileRepo(Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DELETE_FILE_REPO_CLOUD_API_URL, DELETE_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testRemoveFromFileRepoRuntimeException(String url) throws Exception {
    Mockito.when(fileRepoService.removeFromFileRepo(Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  //  @ValueSource(strings = {GET_FILE_REPO_FILE_CLOUD_API_URL, GET_FILE_REPO_FILE_SHIP_API_URL})
  //  @ParameterizedTest
  //  void testGetFileRepoFile(String url) throws Exception {
  //
  // Mockito.when(this.fileRepoService.getFileRepoDetailsById(Mockito.anyLong())).thenReturn(getFileRepo());
  //    this.mockMvc
  //            .perform(
  //                    MockMvcRequestBuilders.get(url, 1L)
  //                            .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                            .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                            .accept(MediaType.APPLICATION_JSON_VALUE))
  //            .andExpect(status().isOk());
  //
  //
  //
  //
  //  }

  // private FileRepo getFileRepo() {
  //    FileRepo fileRepo = new FileRepo();
  //    fileRepo.setFileName("doc");
  //    fileRepo.setFilePath("doccd");
  //    return fileRepo;
  // }

  @ValueSource(strings = {DELETE_FILE_REPO_CLOUD_API_URL, DELETE_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testEditFileRepo(String url) throws Exception {
    Mockito.when(
            fileRepoService.editFileRepo(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(FileRepoSection.class),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenReturn(new FileRepoReply());
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(url, 1L)
                .param("repoId", "100000190")
                .param("section", "Loadable Study")
                .param("category", "200001242")
                .param("hasFileChanged", "true")
                .param("vesselId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DELETE_FILE_REPO_CLOUD_API_URL, DELETE_FILE_REPO_SHIP_API_URL})
  @ParameterizedTest
  void testEditFileRepoRuntimeException(String url) throws Exception {
    Mockito.when(
            fileRepoService.editFileRepo(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(FileRepoSection.class),
                Mockito.anyString(),
                Mockito.anyBoolean(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(url, 1L)
                .param("repoId", "100000190")
                .param("section", "Loadable Study")
                .param("category", "200001242")
                .param("hasFileChanged", "true")
                .param("vesselId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_CLEAR_CACHE_CLOUD_API_URL, GET_CLEAR_CACHE_SHIP_API_URL})
  @ParameterizedTest
  void testClearCache(String url) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url)
                .param("cacheName", "all")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GET_CLEAR_CACHE_CLOUD_API_URL, GET_CLEAR_CACHE_SHIP_API_URL})
  @ParameterizedTest
  void testClearCache1(String url) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url)
                .param("cacheName", "23")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }
}
