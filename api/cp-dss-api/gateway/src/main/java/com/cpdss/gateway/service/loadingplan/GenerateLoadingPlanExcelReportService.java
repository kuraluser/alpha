/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.map.HashedMap;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GenerateLoadingPlanExcelReportService {

  public void generateExcel(OutputStream outStream) {
    String pathTemplateName = "/clientsTemplate.xls";
    Map<String, Object> data = new HashedMap();
    data.put("createdAt", "2021-01-11");
    try (InputStream input = this.getClass().getResourceAsStream(pathTemplateName)) { // 1
      Context context = new Context();

      for (Entry<String, Object> element : data.entrySet()) { // 2
        context.putVar(element.getKey(), element.getValue());
      }

      JxlsHelper.getInstance().processTemplate(input, outStream, context); // 3

    } catch (Exception exception) {
    } finally {
//      closeAndFlushOutput(outStream); // 4
    }
  }

  private void closeAndFlushOutput(OutputStream outStream) {
    try {
      outStream.flush();
      outStream.close();
    } catch (IOException exception) {
    }
  }
}
