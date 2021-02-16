/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestUtils {
  public static Object createDummyObject(Class<?> cls)
      throws InstantiationException, IllegalAccessException {
    Object obj = cls.newInstance();
    for (Field field : obj.getClass().getDeclaredFields()) {
      setFieldValue(field, obj);
    }
    if (obj.getClass().getSuperclass() != null && obj.getClass().getSuperclass() != Object.class) {
      for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
        setFieldValue(field, obj);
      }
    }

    return obj;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void setFieldValue(Field field, Object obj)
      throws IllegalArgumentException, IllegalAccessException {
    field.setAccessible(true);
    if (field.getType().equals(String.class)) {
      field.set(obj, field.getName() + "-test");
    } else if (field.getType().equals(Long.class)) {
      field.set(obj, 1L);
    } else if (field.getType().equals(Integer.class)) {
      field.set(obj, 1);
    } else if (field.getType().equals(BigDecimal.class)) {
      field.set(obj, BigDecimal.ONE);
    } else if (field.getType().equals(List.class)) {
      ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
      Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
      List list = new ArrayList<>();
      if (stringListClass.getName().startsWith("java.")) {
        IntStream.of(1, 3)
            .forEach(
                i -> {
                  try {
                    list.add(createDummyObject(stringListClass));
                  } catch (InstantiationException e) {
                    e.printStackTrace();
                  } catch (IllegalAccessException e) {
                    e.printStackTrace();
                  }
                });
      }
    } else if (field.getType().equals(LocalDateTime.class)) {
      field.set(obj, LocalDateTime.now());
    }
  }
}
