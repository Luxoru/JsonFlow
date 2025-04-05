package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;
import me.luxoru.jsonflow.api.annotation.NodeSerializable;
import me.luxoru.jsonflow.api.serialize.JsonNodeConversionHandler;
import me.luxoru.jsonflow.core.util.ReflectionUtilities;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class JsonConverter {

    private static ObjectMapper objectMapper;

    private static final Map<Class<?>, JsonNodeConversionHandler> objectConverter = new HashMap<>();

    public static <T> ObjectNode deserialize(Object object, Class<T> clazz){
        return deserialize(object, clazz, null);
    }


    public static <T> ObjectNode deserialize(Object object, Class<T> clazz, @Nullable Class<? extends JsonNodeConversionHandler> serializer){
        if(objectConverter.containsKey(clazz)){
            JsonNodeConversionHandler conversionHandler = objectConverter.get(clazz);
            return conversionHandler.deserialize(object, objectMapper);
        }
        if(serializer == null)return null;
        JsonNodeConversionHandler instance = ReflectionUtilities.createInstance(serializer);
        if(instance == null)return null;
        objectConverter.put(clazz, instance);
        return instance.deserialize(object, objectMapper);

    }

    public static <T> T serialize(JsonNode node, Class<T> clazz){
        return serialize(node, clazz, null);
    }

    public static <T> T serialize(JsonNode jsonNode, Class<T> clazz, @Nullable Class<? extends JsonNodeConversionHandler> serializer){
        if(objectConverter.containsKey(clazz)){
            JsonNodeConversionHandler conversionHandler = objectConverter.get(clazz);
            return (T) conversionHandler.serialize(jsonNode);
        }
        if(serializer == null)return null;
        JsonNodeConversionHandler instance = ReflectionUtilities.createInstance(serializer);
        if(instance == null)return null;
        objectConverter.put(clazz, instance);
        return (T) instance.serialize(jsonNode);

    }

    public static <T> T convert(JsonNode node, Class<T> clazz){

        if(objectMapper == null){
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert object to map for class (%s) and node (%s)".formatted(clazz, node.asText()));
        }
    }

}
