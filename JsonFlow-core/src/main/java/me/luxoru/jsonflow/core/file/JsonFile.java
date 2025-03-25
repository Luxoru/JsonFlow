package me.luxoru.jsonflow.core.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.JsonEntity;

@RequiredArgsConstructor
@Getter
public class JsonFile<T extends JsonEntity<T>> {

    private final String name;
    private String parent;
    private final T jsonEntity;


    public void setParent(String parent) {
        if(parent != null){
            throw new IllegalStateException("Cannot have multiple parents:\nAlready: %s\nAdding: %s".formatted(this.getParent(), parent));
        }

        this.parent = parent;
    }
}
