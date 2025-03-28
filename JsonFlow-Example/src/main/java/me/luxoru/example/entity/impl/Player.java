package me.luxoru.example.entity.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import me.luxoru.example.entity.AbstractEntity;
import me.luxoru.example.entity.Entity;
import me.luxoru.example.serializer.impl.PlayerDeserializer;
import me.luxoru.example.util.Position;
import me.luxoru.example.util.serializer.PositionSerializer;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;

@Getter
@JsonDeserialize(using = PlayerDeserializer.class)
@FlowSerializable
public class Player extends AbstractEntity {

    @FlowField(fieldName = "health")
    private float currentHealth;
    @FlowField(fieldName = "position", serializer = PositionSerializer.class)
    private Position position;

    public Player(String name, int width, int height, int maxHealth, float currentHealth, Position position) {
        super(name, width, height, maxHealth);
        this.currentHealth = currentHealth;
        this.position = position;
    }

    public void damage(float damage){
        currentHealth -= damage;
    }
}
