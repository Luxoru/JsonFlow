package me.luxoru.example.util.serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.example.util.Position;
import me.luxoru.jsonflow.core.serializer.node.AbstractJsonEntityConversionHandler;

public final class PositionSerializer extends AbstractJsonEntityConversionHandler<Position> {

    @Override
    public ObjectNode deserialize(Position position) {
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("x", position.getX());
        objectNode.put("y", position.getY());

        return objectNode;

    }

    @Override
    public Position serialize(JsonNode node) {
        JsonNode positionNode = node.get("position");
        float x = positionNode.get("x").floatValue();
        float y = positionNode.get("y").floatValue();

        return new Position(x,y);
    }
}
