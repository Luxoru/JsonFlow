package me.luxoru.jsonflow.core.serializer.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.luxoru.jsonflow.api.serialize.JsonFlowDeserializer;

public abstract class AbstractJsonFlowDeserializer<T> implements JsonFlowDeserializer<T> {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

}
