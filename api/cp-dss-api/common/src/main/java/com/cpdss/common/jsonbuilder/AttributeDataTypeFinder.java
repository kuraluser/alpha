/* Licensed at AlphaOri Technologies */
package com.cpdss.common.jsonbuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeDataTypeFinder extends ClassLoader {
  private static AttributeDataTypeFinder finder = null;
  private static HashMap<String, String> hash_map = new HashMap<String, String>();

  private AttributeDataTypeFinder() {}

  public static String getDataType(String attributeName) {
    try {
      String rootPath = "D:\\Source\\CPTDS\\AOT-CP-DSS-development\\api\\cp-dss-api";
      File rootFolder = new File(rootPath);
      if (finder == null) {
        finder = new AttributeDataTypeFinder();
        List<File> folders = finder.findDirectories(rootFolder, "entity");
        for (File file : folders) {
          finder.generateAttributeInfo(file);
        }
        finder.writeHashMapToCsv();
      }
    } catch (Exception e) {
      return "Not Found";
    }
    return hash_map.getOrDefault(attributeName, "Not Found");
  }

  private List<File> findDirectories(File root, String name) {
    List<File> result = new ArrayList<>();
    for (File file : root.listFiles()) {
      if (file.isDirectory()) {
        if (file.getName().equals(name)) {
          result.add(file);
        }
        result.addAll(findDirectories(file, name));
      }
    }
    return result;
  }

  private void generateAttributeInfo(File dir) {
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File file : directoryListing) {
        try {
          finder.getVariables(file.getPath());
        } catch (Exception e) {

        }
      }
    }
  }

  public ArrayList<String> getVariables(String javaFilePath) {
    ArrayList<String> variableList = new ArrayList<String>();
    BufferedReader br;
    try {
      // creating a buffer reader from the file path
      br = new BufferedReader(new FileReader(new File(javaFilePath)));

      String line;
      while ((line = br.readLine()) != null) {
        if (line.contains("private")) {
          String[] variables = line.split("\\s+");
          String attributeName = variables[3].replace(";", "").trim();
          String dataType = variables[2].trim();

          if (hash_map.getOrDefault(attributeName, "Not Found").equals("Not Found")) {
            if (attributeName.equals("api")) {
              dataType = "BigDecimal";
            } else if (attributeName.equals("sequenceNumber")
                || attributeName.equals("companyId")
                || attributeName.equals("companyXId")
                || attributeName.equals("tankId")
                || attributeName.equals("portId")) {
              dataType = "Integer";
            }
            hash_map.put(attributeName, dataType);
          } else {
            if (hash_map
                .getOrDefault(attributeName, "Not Found")
                .toLowerCase()
                .equals(dataType.toLowerCase())) {
              // System.out.println("Already Exists : " + attributeName);
            } else {
              System.out.println(javaFilePath);
              System.out.println(
                  "Already Exists with different data type : "
                      + attributeName
                      + "   :::  "
                      + dataType);
            }
          }
        }
      }

      br.close();
    } catch (FileNotFoundException e) {
      System.out.println("This is FileNotFoundException error : " + e.getMessage());
    } catch (IOException e) {
      System.out.println("This is IOException error : " + e.getMessage());
    }

    return variableList;
  }

  public void writeHashMapToCsv() throws Exception {
    String eol = System.getProperty("line.separator");
    try (Writer writer = new FileWriter("build/attribute.csv")) {
      for (Map.Entry<String, String> entry : hash_map.entrySet()) {
        writer.append(entry.getKey()).append(',').append(entry.getValue()).append(eol);
      }
    } catch (IOException ex) {
      ex.printStackTrace(System.err);
    }
  }
}
