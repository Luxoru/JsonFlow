package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.Block;

public class TestObjectDeserializer extends AbstractJsonEntityDeserializer{
    @Override
    protected AbstractJsonEntity getEntityData(ObjectNode node) {

        String texture = node.get("texture").asText();

        return new Block(texture);
    }
}
