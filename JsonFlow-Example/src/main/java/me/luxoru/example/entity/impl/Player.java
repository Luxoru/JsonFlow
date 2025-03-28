package me.luxoru.example.entity.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import me.luxoru.example.entity.AbstractEntity;
import me.luxoru.example.entity.Entity;
import me.luxoru.example.serializer.impl.PlayerDeserializer;

@Getter
@JsonDeserialize(using = PlayerDeserializer.class)
public class Player extends AbstractEntity {

    private float currentHealth;

    public Player(String name, int width, int height, int maxHealth) {
        super(name, width, height, maxHealth);
        this.currentHealth = 20.0f;
    }

    public void damage(float damage){
        currentHealth -= damage;
    }



    @Override
    protected ObjectNode toObjectNode() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("health", currentHealth);
        return node;
    }
}
