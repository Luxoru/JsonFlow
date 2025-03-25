package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.Block;

public class TestObjectDeserializer extends AbstractJsonEntityDeserializer<Block>{
    @Override
    protected Block getEntityData(ObjectNode node) {

        String texture = node.get("texture").asText();

        return new Block(texture);
    }

    @Override
    protected Class<Block> getEntityClass() {
        return Block.class;
    }
}
