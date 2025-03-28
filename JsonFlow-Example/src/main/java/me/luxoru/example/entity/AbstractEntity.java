package me.luxoru.example.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

@RequiredArgsConstructor
public abstract class AbstractEntity extends AbstractJsonEntity implements Entity {

    private final String name;
    private final int width;
    private final int height;
    private final float maxHealth;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }


    @Override
    protected ObjectNode thisToJsonObject() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("name", name);
        node.put("width", width);
        node.put("height", height);
        node.put("maxHealth", maxHealth);

        return node;
    }

    protected abstract ObjectNode toObjectNode();
}
