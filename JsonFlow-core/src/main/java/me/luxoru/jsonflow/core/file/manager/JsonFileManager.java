package me.luxoru.jsonflow.core.file.manager;

import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.JsonEntity;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import me.luxoru.jsonflow.core.file.JsonFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JsonFileManager {



    <T extends JsonEntity<T>> JsonFile<T> readFile(File jsonFile, Class<T> jsonClazz) throws FileNotFoundException;

    <T extends JsonEntity<T>> JsonFile<T> readFile(File jsonFile,Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException;

    RawJsonEntity readFileRaw(File jsonFile) throws FileNotFoundException;

    RawJsonEntity readFileRaw(File jsonFile, boolean addFileToCache) throws FileNotFoundException;

    JsonFile<?> getFile(String name);

    Collection<JsonFile<?>> getFiles();

    Set<String> getFileNames();

    Map<String, JsonFile<?>> getFileMap();

}
