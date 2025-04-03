package me.luxoru.jsonflow.api.serialize;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonFlowSerializer<T> {

    T serialize(JsonNode node);

}
