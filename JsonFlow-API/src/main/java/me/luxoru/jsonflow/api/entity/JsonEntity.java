package me.luxoru.jsonflow.api.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonEntity {

    default JsonEntity getParent() {
        return null;
    }

    void setParent(JsonEntity parent);

    String getFileName();

    void setFileName(String fileName);

    ObjectNode toJsonObject() throws JsonProcessingException;

}
