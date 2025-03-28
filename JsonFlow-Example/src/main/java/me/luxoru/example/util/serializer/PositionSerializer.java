package me.luxoru.example.util.serializer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.example.util.Position;
import me.luxoru.jsonflow.core.serializer.node.AbstractJsonFlowDeserializer;

public final class PositionSerializer extends AbstractJsonFlowDeserializer<Position> {

    @Override
    public ObjectNode deserialize(Position position) {
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("x", position.getX());
        objectNode.put("y", position.getY());

        return objectNode;

    }
}
