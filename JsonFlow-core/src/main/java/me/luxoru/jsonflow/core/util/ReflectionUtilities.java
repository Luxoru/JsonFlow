package me.luxoru.jsonflow.core.util;

import java.lang.reflect.Field;

public class ReflectionUtilities {

    public static <T> T getField(Object object,String fieldName, Class<T> fieldClass){
        try {
            Field declaredField = object.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);

            Object obj = declaredField.get(object);

            if(fieldClass.isInstance(obj)){
                return fieldClass.cast(obj);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
        return null;
    }

}
