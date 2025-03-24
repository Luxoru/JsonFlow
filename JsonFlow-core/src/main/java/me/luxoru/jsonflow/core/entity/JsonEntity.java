package me.luxoru.jsonflow.core.entity;

import java.io.File;
import java.util.Map;

public interface JsonEntity {

    default String getParent() {
        return null;
    }

    void setParent(String parent);

    File save(File fileToSave);

    File save();


}
