package me.luxoru.example.serializer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.example.entity.AbstractEntity;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;

public abstract class EntityDeserializer<T extends AbstractEntity> extends AbstractJsonEntityDeserializer<T> {

    @Override
    protected T getEntityData(ObjectNode node) {
        String name = node.get("name").asText();
        int width = node.get("width").asInt();
        int height = node.get("height").asInt();
        int maxHealth = node.get("maxHealth").asInt();
        return getEntityData(name, width, height, maxHealth, node);
    }

    protected abstract T getEntityData(String name, int width, int height, int maxHealth, ObjectNode node);
}
