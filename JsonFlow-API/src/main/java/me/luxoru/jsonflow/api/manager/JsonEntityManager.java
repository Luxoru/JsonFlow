package me.luxoru.jsonflow.api.manager;

import me.luxoru.jsonflow.api.entity.JsonEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface JsonEntityManager {

    <T extends JsonEntity> T readFile(String jsonFileName, Class<T> jsonClazz) throws FileNotFoundException;

    <T extends JsonEntity> T readFile(String jsonFileName, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException;

    <T extends JsonEntity> T readFile(File jsonFile, Class<T> jsonClazz) throws FileNotFoundException;

    <T extends JsonEntity> T readFile(File jsonFile, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException;

    JsonEntity getEntity(String name);

    <T extends JsonEntity> T getEntity(String name, Class<T> clazz);

    Collection<JsonEntity> getEntities();

    Set<String> getFileNames();

    Map<String, JsonEntity> getFileMap();

}
