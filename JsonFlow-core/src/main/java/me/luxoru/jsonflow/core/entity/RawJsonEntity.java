package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.core.serializer.RawJsonEntityDeserializer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Json entity which is represented in kv pairs
 */
@JsonDeserialize(using = RawJsonEntityDeserializer.class)
@RequiredArgsConstructor
public final class RawJsonEntity extends AbstractJsonEntity<RawJsonEntity> {

    private final Map<String, String> pairs;

    @Override
    protected ObjectNode thisToJsonObject() throws JsonProcessingException {
        ObjectNode node = objectMapper.createObjectNode();
        for(Map.Entry<String, String> entry : pairs.entrySet()){
            node.put(entry.getKey(), entry.getValue());
        }

        return node;
    }

    @Override
    public File save(File fileToSave) {
        return null;
    }

    @Override
    public File save() {
        return null;
    }
}
