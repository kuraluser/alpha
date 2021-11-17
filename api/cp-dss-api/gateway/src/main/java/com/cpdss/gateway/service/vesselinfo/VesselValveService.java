/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.vesselinfo;

import com.cpdss.common.generated.VesselInfo;
import com.cpdss.gateway.domain.vessel.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VesselValveService {

  public Map<String, Map<String, Map<String, List<VesselValveSeq>>>> buildVesselValveResponse(
      List<VesselInfo.VesselValveSequence> grpcReplyList) {
    List<VesselValveSequence> list = this.buildVesselValveSequenceDomain(grpcReplyList);
    Map<String, List<VesselValveSequence>> map1 =
        list.stream().collect(Collectors.groupingBy(VesselValveSequence::getSequenceTypeName));
    Map<String, Map<String, Map<String, List<VesselValveSeq>>>> map11 = new HashMap<>();
    for (Map.Entry<String, List<VesselValveSequence>> var2 : map1.entrySet()) {
      Map<String, List<VesselValveSequence>> map2 =
          var2.getValue().stream()
              .collect(Collectors.groupingBy(VesselValveSequence::getSequenceOperationName));

      Map<String, Map<String, List<VesselValveSeq>>> map3 = new HashMap<>();
      for (Map.Entry<String, List<VesselValveSequence>> seqEntityList : map2.entrySet()) {
        Map<String, List<VesselValveSeq>> map4 =
            new TreeMap<>(
                new Comparator<String>() {
                  @Override
                  public int compare(String o1, String o2) {
                    return extractInt(o1) - extractInt(o2);
                  }

                  int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    // return 0 if no digits found
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                  }
                });

        Map<BigDecimal, List<VesselValveSequence>> map22 =
            seqEntityList.getValue().stream()
                .collect(Collectors.groupingBy(VesselValveSequence::getSequenceNumber));

        for (Map.Entry<BigDecimal, List<VesselValveSequence>> entry : map22.entrySet()) {
          map4.put(
              "sequence_" + entry.getKey(),
              entry.getValue().stream()
                  .map(v -> new VesselValveSeq().getInstance(v))
                  .collect(Collectors.toList()));
        }
        map3.put(toCamelCase(seqEntityList.getKey()), map4);
      }
      map11.put(toCamelCase(var2.getKey()), map3);
    }
    return map11;
  }

  public static String toCamelCase(final String init) {
    if (init == null || init.isEmpty()) return null;
    final StringBuilder ret = new StringBuilder(init.length());
    List<String> words = Arrays.asList(init.split(" "));
    for (final String word : words) {
      if (!word.isEmpty()) {
        if (words.get(0).equals(word)) {
          ret.append(Character.toLowerCase(word.charAt(0)));
        } else {
          ret.append(Character.toUpperCase(word.charAt(0)));
        }
        ret.append(word.substring(1).toLowerCase());
      }
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

  public Object buildVesselValveEductorResponse(
      List<VesselInfo.VesselValveEducationProcess> grpcSource) {
    List<VesselValveEducationProcess> sourceList = buildVesselValveEductorDomain(grpcSource);

    Object aa =
        sourceList.stream()
            .collect(
                Collectors.groupingBy(
                    v -> toCamelCase(v.getStepName()),
                    Collectors.groupingBy(
                        v -> toCamelCase(v.getStageName()),
                        Collectors.groupingBy(
                            v -> toCamelCase("stage_" + v.getStageNumber()),
                            Collectors.groupingBy(
                                v -> toCamelCase(v.getEductorName()),
                                Collectors.groupingBy(
                                    v -> "sequence_" + v.getSequenceNumber(),
                                    Collectors.mapping(
                                        v -> createValveEdu(v), Collectors.toList())))))));

    return aa;
  }

  private VesselValveEdu createValveEdu(VesselValveEducationProcess v) {
    return new VesselValveEdu(
        v.getSequenceNumber(),
        v.getValveNumber(),
        v.getValveId(),
        v.getStageName(),
        v.getValveTypeName(),
        v.getTankShortName());
  }

  public List<VesselValveEducationProcess> buildVesselValveEductorDomain(
      List<VesselInfo.VesselValveEducationProcess> sourceList) {
    List<VesselValveEducationProcess> list = new ArrayList<>();
    for (VesselInfo.VesselValveEducationProcess vve : sourceList) {
      VesselValveEducationProcess eduction = new VesselValveEducationProcess();
      BeanUtils.copyProperties(vve, eduction);
      list.add(eduction);
    }
    return list;
  }

  public List<VesselValveAirPurge> buildVesselValveAirPurge(
      List<VesselInfo.VesselValveAirPurgeSequence> vvAirPurgeSequenceList) {
    var resp =
        vvAirPurgeSequenceList.stream()
            .map(
                v -> {
                  VesselValveAirPurge var1 = new VesselValveAirPurge();
                  BeanUtils.copyProperties(v, var1);
                  var1.setIsShut(v.getIsShut());
                  var1.setIsCopWarmup(v.getIsCopWarmup());
                  return var1;
                })
            .collect(Collectors.toList());
    return resp;
  }

  public List<VesselValveStrippingSequence> buildVesselValveStrippingSeq(
      List<VesselInfo.VesselValveStrippingSequence> vvStrippingSequenceList) {
    var resp =
        vvStrippingSequenceList.stream()
            .map(
                v -> {
                  VesselValveStrippingSequence var1 = new VesselValveStrippingSequence();
                  BeanUtils.copyProperties(v, var1);
                  return var1;
                })
            .collect(Collectors.toList());
    return resp;
  }
}
