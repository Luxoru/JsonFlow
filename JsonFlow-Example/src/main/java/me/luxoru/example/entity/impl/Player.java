package me.luxoru.example.entity.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import me.luxoru.example.entity.AbstractEntity;
import me.luxoru.example.serializer.impl.PlayerDeserializer;
import me.luxoru.example.util.Position;
import me.luxoru.example.util.serializer.PositionSerializer;
import me.luxoru.jsonflow.api.annotation.FlowCreator;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowParameter;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;

@Getter
@JsonDeserialize(using = PlayerDeserializer.class)
@FlowSerializable
public class Player extends AbstractEntity {

    private float currentHealth;

    private Position position;

    @FlowCreator
    public Player(String name, int width, int height, int maxHealth, float health,
                  @FlowParameter(value = "position", serializer = PositionSerializer.class) Position position) {
        super(name, width, height, maxHealth);
        this.currentHealth = health;
        this.position = position;
    }

    public void damage(float damage){
        currentHealth -= damage;
    }

    public void move(float dx, float dy){
        this.position.moveX(dx);
        this.position.moveY(dy);
    }
}
