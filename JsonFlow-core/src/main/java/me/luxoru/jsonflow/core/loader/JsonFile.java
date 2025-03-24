package me.luxoru.jsonflow.core.loader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

@RequiredArgsConstructor
@Getter
public class JsonFile {

    private final String name;
    private JsonFile parent;
    private final AbstractJsonEntity jsonEntity;


    public void setParent(JsonFile parent) {
        if(parent != null){
            throw new IllegalStateException("Cannot have multiple parents:\nAlready: %s\nAdding: %s".formatted(this.getParent().getName(), parent.getName()));
        }

        this.parent = parent;
    }
}
