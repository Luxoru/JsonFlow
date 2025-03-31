package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.api.annotation.FlowCreator;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowParameter;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.serialize.JsonFlowConversionHandler;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

import me.luxoru.jsonflow.core.util.ReflectionUtilities;
import me.luxoru.jsonflow.core.util.collection.Tuple;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractJsonEntityDeserializer<T extends AbstractJsonEntity> extends EntityDeserializer<T> {


    @Override
    public final T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectNode node = p.getCodec().readTree(p);
        JsonEntity jsonEntity = mergeWithParentJson(node);

        T entityData;

        Class<T> entityClass = getEntityClass();
        for (Constructor<?> constructor : entityClass.getConstructors()) {
            if(!constructor.isAnnotationPresent(FlowCreator.class))continue;

            //FlowCreator can deserialise
            LinkedHashMap<String, Tuple<Class<?>, Class<? extends JsonFlowConversionHandler>>> constructorParams = new LinkedHashMap<>();

            for (Parameter parameter : constructor.getParameters()) {
                FlowParameter flowParameter = parameter.getAnnotation(FlowParameter.class);
                String fieldName;
                if(flowParameter == null){
                    if(node.get(parameter.getName()) == null){
                        System.out.println("Node %s doesnt exist".formatted(parameter.getName()));
                        continue;
                    }
                    System.out.println("Node %s exists".formatted(parameter.getName()));
                    fieldName = parameter.getName();
                }
                else{
                    fieldName = flowParameter.value();
                }
                Class<?> type = parameter.getType();
                constructorParams.put(fieldName, new Tuple<>(type, flowParameter == null ? JsonFlowConversionHandler.class : flowParameter.serializer()));
            }
            List<Object> objectArr = new ArrayList<>();
            for(var entry : constructorParams.entrySet()){
                JsonNode jsonNode = node.get(entry.getKey());
                if(jsonNode == null){
                    throw new IllegalStateException("JsonNode not found (%s)".formatted(entry.getKey()));
                }

                var tuple = entry.getValue();
                Class<?> objectClass = tuple.getFirst();

                Class<? extends JsonFlowConversionHandler> serializer = tuple.getSecond();

                if (serializer.equals(JsonFlowConversionHandler.class)) {
                    Object value = JsonConverter.serialize(node.get(entry.getKey()), objectClass);
                    if(value == null){
                        value = JsonConverter.convert(node.get(entry.getKey()), objectClass);
                    }
                    objectArr.add(value);
                    continue;
                }

                Object serializedObject = JsonConverter.serialize(node, objectClass, serializer);


                objectArr.add(serializedObject);
            }

            try{
                entityData = (T) constructor.newInstance(objectArr.toArray());
                if(jsonEntity != null){
                    entityData.setParent(jsonEntity);
                }
                System.out.println("CONSTRUCTOR");
                return entityData;
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }


        }


        entityData = getEntityData(node);

        if(jsonEntity != null){
            entityData.setParent(jsonEntity);
        }


        return entityData;
    }

    protected T getEntityData(ObjectNode node){return null;};



}
