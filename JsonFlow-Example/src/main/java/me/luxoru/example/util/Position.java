package me.luxoru.example.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.luxoru.jsonflow.core.serializer.node.AbstractJsonFlowDeserializer;

@AllArgsConstructor
@Getter
@Setter
public final class Position {
    private float x;
    private float y;

    public Position moveX(final float xIncrement){
        this.x += xIncrement;
        return this;
    }

    public Position moveY(final float yIncrement){
        this.y += yIncrement;
        return this;
    }





}



