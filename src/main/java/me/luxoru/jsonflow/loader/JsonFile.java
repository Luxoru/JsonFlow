package me.luxoru.jsonflow.loader;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.entity.AbstractJsonEntity;

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
