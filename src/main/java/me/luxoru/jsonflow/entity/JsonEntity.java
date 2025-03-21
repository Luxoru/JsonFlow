package me.luxoru.jsonflow.entity;

import java.io.File;
import java.util.Map;

public interface JsonEntity {

    default String getParent() {
        return null;
    }

    File save(File fileToSave);

    File save();


}
