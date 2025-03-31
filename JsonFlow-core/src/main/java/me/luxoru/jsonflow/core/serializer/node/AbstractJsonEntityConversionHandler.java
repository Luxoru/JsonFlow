package me.luxoru.jsonflow.core.serializer.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import me.luxoru.jsonflow.api.serialize.JsonFlowDeserializer;
import me.luxoru.jsonflow.api.serialize.JsonFlowSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractJsonEntity<T> implements JsonFlowDeserializer<T>, JsonFlowSerializer<T> {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

}
