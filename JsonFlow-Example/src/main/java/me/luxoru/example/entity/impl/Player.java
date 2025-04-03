package me.luxoru.example.entity.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import me.luxoru.example.entity.AbstractEntity;
import me.luxoru.example.serializer.impl.PlayerDeserializer;
import me.luxoru.example.util.Position;
import me.luxoru.example.util.serializer.PositionSerializer;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;

@Getter
@JsonDeserialize(using = PlayerDeserializer.class)
@FlowSerializable
public class Player extends AbstractEntity {

    private float health;

    @FlowField(value = "position", serializer = PositionSerializer.class)
    private Position position;

    public void damage(float damage){
        health -= damage;
    }

    public void move(float dx, float dy){
        this.position.moveX(dx);
        this.position.moveY(dy);
    }
}
