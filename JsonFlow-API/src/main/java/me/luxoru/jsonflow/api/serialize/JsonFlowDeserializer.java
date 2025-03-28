package me.luxoru.jsonflow.api.serialize;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonFlowDeserializer<T> {

    ObjectNode deserialize(T object);

}
