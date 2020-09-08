package com.lpb.lifecardsdk.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectUtils {

    public static <T, R> R convertObject(T data, Class<R> clss) {
        if (data == null) return null;
        try {
            Constructor<R> constructor = clss.getConstructor();
            R result = constructor.newInstance();
            for (Field field : clss.getDeclaredFields()) {
                field.setAccessible(true);
                for (Field f : data.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (field.getName().equals(f.getName())) {
                        field.set(result, f.get(data));
                        break;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            Logger.e(ObjectUtils.class, e);
            return null;
        }
    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }

    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

}
