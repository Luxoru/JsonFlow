package me.luxoru.example.util.serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.example.util.Position;
import me.luxoru.jsonflow.api.serialize.JsonNodeConversionHandler;

public class PositionSerializer implements JsonNodeConversionHandler<Position> {
    @Override
    public ObjectNode deserialize(Position object, ObjectMapper mapper) {

        System.out.println("Using pos deserializer");

        ObjectNode positionNode = mapper.createObjectNode();

        positionNode.put("x", object.getX());
        positionNode.put("y", object.getY());

        return positionNode;

    }

    @Override
    public Position serialize(JsonNode node) {

        System.out.println("Using pos serializer");
        float x = node.get("x").floatValue();
        float y = node.get("y").floatValue();

        return new Position(x,y);
    }
}
