package me.luxoru.example.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;

@Getter
@Setter
@NoArgsConstructor
public final class Position {

    private float x;
    private float y;

    public Position(final float x, final float y){
        this.x = x;
        this.y = y;
    }

    public Position moveX(final float xIncrement){
        this.x += xIncrement;
        return this;
    }

    public Position moveY(final float yIncrement){
        this.y += yIncrement;
        return this;
    }

}



