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

    public Position moveX(final int xIncrement){
        this.x += x;
        return this;
    }

    public Position moveY(final int yIncrement){
        this.y += y;
        return this;
    }
}
