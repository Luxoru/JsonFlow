package me.luxoru.jsonflow.core.serializer.node;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonNode {

    private final ObjectNode objectNode;

    public JsonNode(ObjectNode objectNode) {
        this.objectNode = objectNode;
    }


    public String asText(){
        if(this.objectNode == null)return null;
        return this.objectNode.asText();
    }
}
