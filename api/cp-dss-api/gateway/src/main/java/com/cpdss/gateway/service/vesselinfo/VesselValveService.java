/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.vesselinfo;

import com.cpdss.common.generated.VesselInfo;
import com.cpdss.gateway.domain.vessel.VesselValveSeq;
import com.cpdss.gateway.domain.vessel.VesselValveSequence;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VesselValveService {

  public Map<String, Map<String, Map<String, VesselValveSeq>>> buildVesselValveResponse(
      List<VesselInfo.VesselValveSequence> grpcReplyList) {
    List<VesselValveSequence> list = this.buildVesselValveSequenceDomain(grpcReplyList);
    Map<String, List<VesselValveSequence>> map1 =
        list.stream().collect(Collectors.groupingBy(VesselValveSequence::getSequenceTypeName));
    Map<String, Map<String, Map<String, VesselValveSeq>>> map11 = new HashMap<>();
    for (Map.Entry<String, List<VesselValveSequence>> var2 : map1.entrySet()) {
      Map<String, List<VesselValveSequence>> map2 =
          var2.getValue().stream()
              .collect(Collectors.groupingBy(VesselValveSequence::getSequenceOperationName));
      Map<String, Map<String, VesselValveSeq>> map3 = new HashMap<>();
      for (Map.Entry<String, List<VesselValveSequence>> seqEntityList : map2.entrySet()) {
        Map<String, VesselValveSeq> map4 = new HashMap<>();
        int x = 1;
        for (VesselValveSequence v : seqEntityList.getValue()) {
          map4.put("Sequence_" + x, new VesselValveSeq().getInstance(v));
          x++;
        }
        map3.put(toCamelCase(seqEntityList.getKey()), map4);
      }
      map11.put(var2.getKey(), map3);
    }
    return map11;
  }

  public static String toCamelCase(final String init) {
    if (init == null) return null;

    final StringBuilder ret = new StringBuilder(init.length());

    for (final String word : init.split(" ")) {
      if (!word.isEmpty()) {
        ret.append(Character.toUpperCase(word.charAt(0)));
        ret.append(word.substring(1).toLowerCase());
      }
      if (!(ret.length() == init.length())) ret.append(" ");
    }

    return ret.toString();
  }

  public List<VesselValveSequence> buildVesselValveSequenceDomain(
      List<VesselInfo.VesselValveSequence> list) {
    List<VesselValveSequence> sequenceList = new ArrayList<>();
    for (VesselInfo.VesselValveSequence vvs : list) {
      VesselValveSequence sequence = new VesselValveSequence();
      Optional.ofNullable(vvs.getSequenceNumber())
          .ifPresent(
              v -> {
                if (!v.isEmpty()) sequence.setSequenceNumber(new BigDecimal(v));
              });
      BeanUtils.copyProperties(vvs, sequence);
      sequenceList.add(sequence);
    }
    return sequenceList;
  }
}
