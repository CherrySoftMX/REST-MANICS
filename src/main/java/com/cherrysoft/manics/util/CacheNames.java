package com.cherrysoft.manics.util;

import com.cherrysoft.manics.exception.v2.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheNames {
  public static final String BOOLEAN = "boolean";
  public static final String DOUBLE = "double";

  public static String[] getCacheNames() {
    try {
      final Field[] fields = CacheNames.class.getDeclaredFields();
      final String[] cacheNames = new String[fields.length];
      for (int i = 0; i < fields.length; i++) {
        cacheNames[i] = (String) fields[i].get(fields[i].getName());
      }
      return cacheNames;
    } catch (IllegalAccessException e) {
      throw new ApplicationException(e);
    }
  }

}
