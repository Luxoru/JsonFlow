package me.luxoru.jsonflow.api.entity;

import java.io.File;

public interface PersistableEntity<T extends PersistableEntity<T>> extends JsonEntity<T>{

    File save(File fileToSave);

    File save();

}
