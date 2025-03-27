package me.luxoru.jsonflow.api.entity;

import java.io.File;

public interface JsonEntity<T extends JsonEntity<T>> {



    default JsonEntity<?> getParent() {
        return null;
    }

    <V extends JsonEntity<V>> void setParent(JsonEntity<V> parent);

    String getFileName();

    void setFileName(String fileName);


}
