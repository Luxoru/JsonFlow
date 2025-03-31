package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.api.annotation.FlowCreator;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.serialize.JsonFlowDeserializer;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

import me.luxoru.jsonflow.core.util.ReflectionUtilities;
import me.luxoru.jsonflow.core.util.collection.Tuple;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractJson<T extends AbstractJsonEntity> extends EntityDeserializer<T> {


    @Override
    public final T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectNode node = p.getCodec().readTree(p);
        JsonEntity jsonEntity = mergeWithParentJson(node);

        Class<T> entityClass = getEntityClass();
        for (Constructor<?> constructor : entityClass.getConstructors()) {
            if(!constructor.isAnnotationPresent(FlowCreator.class))continue;

            //FlowCreator can deserialise
            LinkedHashMap<String, Tuple<Class<?>, Class<? extends JsonFlowDeserializer>>> constructorParams = new LinkedHashMap<>();
            
            for (Parameter parameter : constructor.getParameters()) {
                FlowField flowField = parameter.getAnnotatedType().getAnnotation(FlowField.class);
                if(flowField == null)continue;
                String fieldName = flowField.fieldName();
                Class<?> type = parameter.getType();
                constructorParams.put(fieldName, new Tuple<>(type, flowField.serializer()));
            }

            for(var entry : constructorParams.entrySet()){
                JsonNode jsonNode = node.get(entry.getKey());

                if(jsonNode == null){
                    throw new IllegalStateException("JsonNode not found (%s)".formatted(entry.getKey()));
                }

                var tuple = entry.getValue();
                Class<?> first = tuple.getFirst();
                Object value = JsonConverter.convert(node.get(entry.getKey()), first);
                Class<? extends JsonFlowDeserializer> serializer = tuple.getSecond();

                List<Object> objectArr = new ArrayList<>();

                if (serializer.equals(JsonFlowDeserializer.class)) {
                    objectArr.add(value);
                    continue;
                }

                JsonFlowDeserializer instance = ReflectionUtilities.createInstance(serializer);
                if(instance == null)return null;
                ObjectNode deserialize = instance.deserialize(value);






            }


        }


        T entityData = getEntityData(node);


        if(jsonEntity != null){
            entityData.setParent(jsonEntity);
        }

        return entityData;
    }

    protected abstract T getEntityData(ObjectNode node);



}
