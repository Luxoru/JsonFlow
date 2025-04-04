package me.luxoru.jsonflow.api.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonNodeDeserializer<T> {

    ObjectNode deserialize(T object, ObjectMapper mapper);

}
