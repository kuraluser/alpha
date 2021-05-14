/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.vesselinfo.entity.HydrostaticTable;
import com.cpdss.vesselinfo.entity.Vessel;
import java.math.BigDecimal;
import java.util.List;

public interface HydrostaticService {

  List<HydrostaticTable> fetchAllDataByDraftAndVessel(Vessel var1, BigDecimal var2);
}
