package me.luxoru.jsonflow.core.entity;

import me.luxoru.jsonflow.core.file.JsonFile;

import java.io.File;
import java.util.Map;

public interface JsonEntity<T extends JsonEntity<T>> {



    default JsonEntity<?> getParent() {
        return null;
    }

    JsonFile<T> getJsonFile();

    void setJsonFile(JsonFile<T> jsonFile);

    void setParent(JsonEntity<? extends JsonEntity<?>> parent);

    File save(File fileToSave);

    File save();


}
