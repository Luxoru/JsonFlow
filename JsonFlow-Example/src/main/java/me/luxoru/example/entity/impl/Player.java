package me.luxoru.example.entity.impl;

import lombok.Getter;
import me.luxoru.example.entity.AbstractEntity;
import me.luxoru.example.util.Position;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;

@Getter
@FlowSerializable
public class Player extends AbstractEntity {

    private float health;
    private Position position;

    public void damage(float damage){
        health -= damage;
    }

    public void move(float dx, float dy){
        this.position.moveX(dx);
        this.position.moveY(dy);
    }
}
