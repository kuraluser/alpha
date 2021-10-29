/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.communication.repository.StagingRepository;
import com.cpdss.common.exception.GenericServiceException;
import java.util.Arrays;
import java.util.List;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @Author Selvy Thomas
 *
 * <p>Class for writing test cases for StagingService
 */
@SpringJUnitConfig(classes = {StagingService.class})
public class StagingServiceTest {

  private static DataTransferStage dataTransferStage;
  private static String jsonResult = null;
  @Autowired private StagingService stagingService;

  @MockBean private StagingRepository stagingRepository;

  @BeforeAll
  public static void beforeAll() {
    dataTransferStage =
        new DataTransferStage(
            "hsgdfgsdjgsdbgksgfskgiytu786q7ffef",
            "lodable_study",
            "user",
            "table",
            null,
            "draft",
            "status description");
    dataTransferStage.setId((long) 1);
    dataTransferStage.setStatus("draft");
    dataTransferStage.setCreatedBy("communication");
    dataTransferStage.setLastModifiedBy("communication");
    jsonResult =
        "[{\"data\":[{\"id\":1000000,\"user_name\":\"john\",\"designation\":\"tester\",\"created_by\":\"admin\"},{\"id\":1000001,\"user_name\":\"smith\",\"designation\":\"developer\",\"created_by\":\"admin\"}],\"meta_data\":{\"processIdentifier\":\"user\",\"processId\":\"hsgdfgsdjgsdbgksgfskgiytu786q7ffef\",\"processGroupId\":\"lodable_study\"}}]";
  }

  @Test
  public void saveTest() throws GenericServiceException {

    Mockito.when(this.stagingRepository.save(ArgumentMatchers.any(DataTransferStage.class)))
        .thenReturn(dataTransferStage);
    stagingService.save(jsonResult);
    // assertEquals((long) 1, ds.getId());
  }

  /*  @Test
  public void saveTestNegativeCase() throws GenericServiceException {

    Mockito.when(this.stagingRepository.save(ArgumentMatchers.any(DataTransferStage.class)))
        .thenThrow(ResourceAccessException.class);
    DataTransferStage ds = stagingService.save(jsonResult);
    // assertEquals((long) 1, ds.getId());
  }*/

  @Test
  public void getByProcessIdTest() throws GenericServiceException {

    Mockito.when(this.stagingRepository.findByProcessId(any()))
        .thenReturn(Arrays.asList(dataTransferStage));
    List<DataTransferStage> ds = stagingService.getByProcessId(RandomString.make(5));
    // assertEquals((long) 1, ds.getId());
  }

  @Test
  public void isAllDataReceivedTest() throws GenericServiceException {
    List<String> list = Arrays.asList("user");
    Mockito.when(this.stagingRepository.findByProcessId(any()))
        .thenReturn(Arrays.asList(dataTransferStage));
    Boolean ds = stagingService.isAllDataReceived("hsgdfgsdjgsdbgksgfskgiytu786q7ffef", list);
    // assertEquals((long) 1, ds.getId());
  }

  @Test
  public void isAllDataReceivedTestNegativeCase() throws GenericServiceException {
    List<String> list = Arrays.asList("user");
    Mockito.when(this.stagingRepository.findByProcessId(any()))
        .thenReturn(Arrays.asList(dataTransferStage));
    Boolean ds = stagingService.isAllDataReceived(RandomString.make(5), list);
    // assertEquals((long) 1, ds.getId());
  }
}
