/* Licensed at AlphaOri Technologies */
package com.cpdss.common.jsonbuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.FileWriter;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.reflections.Reflections;

@Slf4j
public class ParserUtil {

  static String packageName = null;
  static Set<String> classRegister = new HashSet<>();

  public static void parserMain(String packageName) {
    ParserUtil.packageName = packageName;
    Reflections reflections = new Reflections(packageName);
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(CPDSSJsonParser.class);
    log.info("Total class with @Entity annotation {}", annotated.size());

    Map<String, Object> mapData = new HashMap<>();
    Map<String, Object> errorData = new HashMap<>();
    for (Class<?> tClass : annotated) {
      try {
        Object classObject = getFillWithData(tClass, new String[] {""});
        log.info("after set value {}", classObject);
        mapData.put(tClass.getName(), classObject);
      } catch (Exception e) {
        e.printStackTrace();
        errorData.put(tClass.getName(), e.getMessage());
      }
    }
    log.info("convert total {}", mapData.size());
    log.info("error total {}", errorData.size());
    long fileName = System.currentTimeMillis();
    writeToFile(mapData, "build/" + fileName + "suc.json");
    // writeToFile(errorData, "build/" + fileName + "fai.json");
  }

  private static boolean validateClassRegister(String vl) {
    return classRegister.contains(vl);
  }

  private static Object getFillWithData(Class<?> tClass, String[] args)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {

    if (!validateClassRegister(tClass.getName())
        && tClass.getPackage() != null
        && tClass.getPackage().getName().equalsIgnoreCase(packageName)) {
      classRegister.add(tClass.getName());
    } else if (validateClassRegister(tClass.getName())
        && tClass.getPackage().getName().equalsIgnoreCase(packageName)) {
      return null;
    }

    Constructor<?>[] constructors = tClass.getConstructors();
    Object classObject = null;
    for (Constructor cst : constructors) {
      Type consPrams[] = cst.getGenericParameterTypes();
      log.info("Const Class {}, Size - {}", tClass.getName(), consPrams.length);
      if (consPrams.length == 0) {
        if (classObject == null) {
          classObject = cst.newInstance();
        }
      }
      if (consPrams.length == 1) {
        if (classObject == null) {
          Class<?> pType = (Class<?>) consPrams[0];
          Object val = getDummyValueForClass(pType, new String[] {"string-number"}, null);
          classObject = cst.newInstance(val);
        }
      }
    }
    if (classObject == null && constructors.length > 0) {
      classObject = constructors[0].newInstance((Object) args);
    }

    Field[] classFields = tClass.getDeclaredFields();
    if (tClass.getPackage() != null
        && tClass.getPackage().getName().equalsIgnoreCase(packageName)) {

      for (Field field : classFields) {
        Class<?> fieldClassType = field.getType();
        Object val = getDummyValueForClass(fieldClassType, args, field);
        try {
          BeanUtils.setProperty(classObject, field.getName(), val);
        } catch (Exception e) {
          log.error(
              "set bean error - Object {}, Field {}, Value {}", classObject, field.getName(), val);
        }
      }
      Class<?> superclass = tClass.getSuperclass();
      Field[] parentFields = superclass.getDeclaredFields();
      for (Field field : parentFields) {
        Class<?> fieldClassType = field.getType();
        Object val = getDummyValueForClass(fieldClassType, args, field);
        BeanUtils.setProperty(classObject, field.getName(), val);
      }
    }
    return classObject;
  }

  private static Object getDummyValueForClass(Class aClass, String[] args, Field field)
      throws IllegalAccessException, InvocationTargetException, InstantiationException {

    if (aClass.getName().equalsIgnoreCase(String.class.getName())) {
      if (args != null && args.length > 0) {
        if (args[0] == "string-number") {
          return "22";
        }
      }
      if (field != null && field.getName().toLowerCase().contains("time")) {
        return "12-01-2021 12:12";
      }
      return "lorem ipsum dolor";
    } else if (aClass.getName().equalsIgnoreCase(CharSequence.class.getName())) {
      return "lorem ipsum";
    } else if (aClass.getName().equalsIgnoreCase(Character.class.getName())) {
      return 'c';
    } else if (aClass.getName().equalsIgnoreCase(long.class.getName())) {
      return 2L;
    } else if (aClass.getName().equalsIgnoreCase(Long.class.getName())) {
      return 2L;
    } else if (aClass.getName().equalsIgnoreCase(BigDecimal.class.getName())) {
      return 3.003;
    } else if (aClass.getName().equalsIgnoreCase(boolean.class.getName())) {
      return true;
    } else if (aClass.getName().equalsIgnoreCase(Boolean.class.getName())) {
      return true;
    } else if (aClass.getName().equalsIgnoreCase(Integer.class.getName())) {
      return 123;
    } else if (aClass.getName().equalsIgnoreCase(int.class.getName())) {
      return 123;
    } else if (aClass.getName().equalsIgnoreCase(Timestamp.class.getName())) {
      return new Timestamp(123);
    } else if (aClass.getName().equalsIgnoreCase(Date.class.getName())) {
      return new Date();
    } else if (aClass.getName().equalsIgnoreCase(Time.class.getName())) {
      return new Time(123);
    } else if (aClass.getName().equalsIgnoreCase(List.class.getName())) {
      ParameterizedType genericType = (ParameterizedType) field.getGenericType();
      return setCollectionData(aClass, args, (Class<?>) genericType.getActualTypeArguments()[0]);
    } else if (aClass.getName().equalsIgnoreCase(Set.class.getName())) {
      ParameterizedType genericType = (ParameterizedType) field.getGenericType();
      return setCollectionData(aClass, args, (Class<?>) genericType.getActualTypeArguments()[0]);
    } else {
      return getFillWithData(aClass, args);
    }
  }

  private static Collection<Object> setCollectionData(
      Class aClass, String[] args, Class genericType)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {
    if (List.class.getName().toLowerCase().equals(aClass.getName().toLowerCase())) {
      List<Object> aList = new ArrayList<>();
      aList.add(ParserUtil.getFillWithData(genericType, args));
      aList.add(ParserUtil.getFillWithData(genericType, args));
      return aList;
    } else if (Set.class.getName().toLowerCase().equals(aClass.getName().toLowerCase())) {
      Set<Object> aSet = new HashSet<>();
      aSet.add(ParserUtil.getFillWithData(genericType, args));
      aSet.add(ParserUtil.getFillWithData(genericType, args));
      return aSet;
    }
    return null;
  }

  public static void writeToFile(Object o, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName)) {
      ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      String json = ow.writeValueAsString(o);
      fileWriter.write(json);
      fileWriter.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
