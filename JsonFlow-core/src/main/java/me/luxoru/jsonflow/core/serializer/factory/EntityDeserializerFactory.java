package me.luxoru.jsonflow.core.serializer.factory;

import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;

public class EntityDeserializerFactory {

    public static <T extends JsonEntity> AbstractJsonEntityDeserializer<T>  createDeserializer(Class<T> clazz){
        return new AbstractJsonEntityDeserializer<>(true) {
            @Override
            public Class<T> getEntityClass() {
                return clazz;
            }
        };
    }

}
