package me.luxoru.jsonflow.api.entity;

import java.io.File;

public interface PersistableEntity extends JsonEntity {

    File save(File fileToSave);

    File save();

}
