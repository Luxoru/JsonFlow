package me.luxoru.jsonflow.api.serialize;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonFlowSerializer<T> {

    T serialize(JsonNode node);

}
