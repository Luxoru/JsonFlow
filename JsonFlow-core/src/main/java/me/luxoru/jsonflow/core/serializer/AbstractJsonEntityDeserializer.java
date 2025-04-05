package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowIgnore;
import me.luxoru.jsonflow.api.annotation.NodeSerializable;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.serialize.JsonNodeConversionHandler;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.util.ReflectionUtilities;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class AbstractJsonEntityDeserializer<T extends JsonEntity> extends EntityDeserializer<T> {

    public AbstractJsonEntityDeserializer(){
        super();
    }

    public AbstractJsonEntityDeserializer(boolean anonymous){
        super(anonymous);
    }


    @Override
    public final T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectNode node = p.getCodec().readTree(p);
        List<JsonEntity> jsonEntity = mergeWithParentJson(node);

        T entityData;

        Class<T> entityClass = getEntityClass();

        try {
            Constructor<T> declaredConstructor = entityClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            entityData = declaredConstructor.newInstance();

            List<Field> allFields = ReflectionUtilities.getAllFieldsReversed(entityClass, AbstractJsonEntity.class);

            for(Field field : allFields){
                field.setAccessible(true);
                if(field.isAnnotationPresent(FlowIgnore.class))continue;
                FlowField flowField = field.getAnnotation(FlowField.class);
                JsonNode fieldName;
                Class<?> fieldType = field.getType();
                if(flowField == null){
                    fieldName = node.get(field.getName());
                    if(fieldName == null){
                        System.out.printf("Field %s has no data set. Setting as null\n", field.getName());
                        continue;
                    }
                    Object serialized;

                    NodeSerializable nodeSerializable = fieldType.getAnnotation(NodeSerializable.class);

                    if(nodeSerializable != null){
                        serialized = JsonConverter.serialize(fieldName, fieldType, nodeSerializable.serializer());
                    }
                    else{
                        serialized = JsonConverter.serialize(fieldName, fieldType);
                    }


                    if(serialized == null){
                        if(nodeSerializable == null){
                            serialized = JsonConverter.convert(fieldName, fieldType);
                        }
                    }
                    field.set(entityData, serialized);
                    continue;
                }

                Class<? extends JsonNodeConversionHandler> serializer = flowField.serializer();

                fieldName = node.get((flowField.value().equalsIgnoreCase("") ? field.getName() : flowField.value()));

                if(fieldName == null){
                    System.out.printf("Field %s has no data set. Setting as null\n", field.getName());
                    continue;
                }

                if(serializer.equals(JsonNodeConversionHandler.class)){
                    Object serialized = JsonConverter.serialize(fieldName, fieldType);

                    if(serialized == null){
                        serialized = JsonConverter.convert(fieldName, fieldType);
                    }
                    field.set(entityData, serialized);
                    continue;
                }

                Object serializedObject = JsonConverter.serialize(fieldName, fieldType, serializer);

                field.set(entityData, serializedObject);

            }

            return entityData;


        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Default constructor doesnt exist");
        }



        entityData = getEntityData(node);

        if(entityData == null){
            return null;
        }

        if(jsonEntity != null){
            entityData.addParents(jsonEntity);
        }


        return entityData;
    }

    protected T getEntityData(ObjectNode node){return null;};



}
