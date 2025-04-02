package me.luxoru.example.serializer.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.example.entity.impl.Player;
import me.luxoru.example.serializer.EntityDeserializer;
import me.luxoru.example.util.Position;

public class PlayerDeserializer extends EntityDeserializer<Player> {
    @Override
    protected Player getEntityData(String name, int width, int height, int maxHealth, ObjectNode node) {
        JsonNode jsonNode = node.get("position");

        int x = jsonNode.get("x").asInt();
        int y = jsonNode.get("y").asInt();

        return null;
    }
}
