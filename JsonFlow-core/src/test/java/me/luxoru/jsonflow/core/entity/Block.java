package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.api.entity.PersistableEntity;
import me.luxoru.jsonflow.core.serializer.TestObjectDeserializer;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Getter
@JsonDeserialize(using = TestObjectDeserializer.class)
public class Block extends AbstractJsonEntity<Block> implements PersistableEntity<Block> {

    private final String texture;
    public final int[][] positions;


    @Override
    protected ObjectNode thisToJsonObject() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("texture", texture);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (int[] pos : positions) {
            ArrayNode innerArray = objectMapper.createArrayNode();
            for (int value : pos) {
                innerArray.add(value);
            }
            arrayNode.add(innerArray);
        }

        node.set("positions", arrayNode);
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
