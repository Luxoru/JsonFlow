package me.luxoru.jsonflow.core.serializer.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.luxoru.jsonflow.api.serialize.JsonFlowConversionHandler;
import me.luxoru.jsonflow.api.serialize.JsonFlowDeserializer;
import me.luxoru.jsonflow.api.serialize.JsonFlowSerializer;

public abstract class AbstractJsonEntityConversionHandler<T> implements JsonFlowConversionHandler<T> {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

}
