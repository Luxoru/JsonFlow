package me.luxoru.jsonflow.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ReflectionUtilities {

    public static <T> T getField(Object object, String fieldName, Class<T> fieldClass) {
        try {
            Class<?> clazz = object.getClass();
            Field field = null;

            // Traverse up the class hierarchy to find the field
            while (clazz != null) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break; // Stop when the field is found
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass(); // Move to parent class
                }
            }

            // If field is still null, it means it wasn't found
            if (field == null) {
                return null;
            }

            field.setAccessible(true);
            Object obj = field.get(object); // Get the field value

            if (obj == null) {
                return null;
            }

            // Handle primitive type conversions manually
            if (fieldClass == int.class) {
                return (T) (Integer) obj;
            }
            if (fieldClass == double.class) {
                return (T) (Double) obj;
            }
            if (fieldClass == float.class) {
                return (T) (Float) obj;
            }
            if (fieldClass == long.class) {
                return (T) (Long) obj;
            }
            if (fieldClass == boolean.class) {
                return (T) (Boolean) obj;
            }
            if (fieldClass == char.class) {
                return (T) (Character) obj;
            }
            if (fieldClass == short.class) {
                return (T) (Short) obj;
            }
            if (fieldClass == byte.class) {
                return (T) (Byte) obj;
            }

            // For non-primitive types, use regular casting
            if (fieldClass.isInstance(obj)) {
                return fieldClass.cast(obj);
            }

        } catch (IllegalAccessException e) {
            return null;
        }
        return null;
    }


    public static <T> T createInstance(Class<T> clazz, Object... params) throws RuntimeException {
        Class<?>[] types = new Class<?>[params.length];

        int index = 0;
        for (Object param : params) {
            if (param == null) {
                types[index] = Object.class; // Handle nulls explicitly
            } else {
                types[index] = param.getClass();
            }
            index++;
        }

        try {

            Constructor<T> constructor = clazz.getDeclaredConstructor(types);
            constructor.setAccessible(true);
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return clazz == Integer.class || clazz == Double.class || clazz == Float.class ||
                clazz == Long.class || clazz == Short.class || clazz == Byte.class ||
                clazz == Boolean.class || clazz == Character.class;
    }


    public static List<Field> getAllFields(Class<?> clazz){
        List<Field> allFields = new ArrayList<>();
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            allFields.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }

        return allFields;
    }



}
