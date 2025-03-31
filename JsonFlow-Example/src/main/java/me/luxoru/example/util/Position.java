package me.luxoru.example.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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



