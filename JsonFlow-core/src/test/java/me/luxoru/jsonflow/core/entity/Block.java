package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.core.serializer.TestObjectDeserializer;

import java.io.File;

@RequiredArgsConstructor
@Getter
@JsonDeserialize(using = TestObjectDeserializer.class)
public class Block extends AbstractJsonEntity{

    private final String texture;


    @Override
    protected ObjectNode thisToJsonObject() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("texture", texture);
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
