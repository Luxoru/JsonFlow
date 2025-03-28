package me.luxoru.example.entity;

import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

@RequiredArgsConstructor
public abstract class AbstractEntity extends AbstractJsonEntity implements Entity {

    @FlowField(fieldName = "name")
    private final String name;
    @FlowField(fieldName = "width")
    private final int width;
    @FlowField(fieldName = "height")
    private final int height;
    @FlowField(fieldName = "maxHealth")
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
}
