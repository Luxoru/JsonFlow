package me.luxoru.jsonflow.core.file.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.luxoru.jsonflow.core.entity.JsonEntity;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import me.luxoru.jsonflow.core.file.JsonFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AbstractJsonFileManager implements JsonFileManager{

    private final Map<String, JsonFile<?>> jsonFileMap;

    private final ObjectMapper mapper = new ObjectMapper();

    public AbstractJsonFileManager() {
        jsonFileMap = new HashMap<>();
    }

    @Override
    public <T extends JsonEntity<T>> JsonFile<T> readFile(File jsonFile, Class<T> jsonClazz) throws FileNotFoundException {

        return readFile(jsonFile, jsonClazz,true);

    }

    @Override
    public <T extends JsonEntity<T>> JsonFile<T> readFile(File file, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException {
        if(!file.exists()){
            throw new FileNotFoundException("File "+file.getName()+" doesnt exist!");
        }

        try(FileReader reader = new FileReader(file)){

            T jsonEntity = mapper.readValue(reader, jsonClazz);

            JsonFile<T> jsonFile = new JsonFile<>(file.getName(), jsonEntity);

            if(jsonEntity == null){
                throw new IllegalStateException("SMT DEFO WRONG");
            }

            jsonEntity.setJsonFile(jsonFile);

            if(addFileToCache){
                if(jsonEntity.getJsonFile() == null)throw new IllegalStateException("SMT WRONG");
                jsonFileMap.put(jsonEntity.getJsonFile().getName(), jsonEntity.getJsonFile());
            }
            return jsonEntity.getJsonFile();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RawJsonEntity readFileRaw(File jsonFile) throws FileNotFoundException {
        return readFile(jsonFile, RawJsonEntity.class).getJsonEntity();
    }

    @Override
    public RawJsonEntity readFileRaw(File jsonFile, boolean addFileToCache) throws FileNotFoundException {
        return null;
    }

    @Override
    public JsonFile<?> getFile(String name) {
        return jsonFileMap.get(name);
    }

    @Override
    public Collection<JsonFile<?>> getFiles() {
        return jsonFileMap.values();
    }

    @Override
    public Set<String> getFileNames() {
        return jsonFileMap.keySet();
    }

    @Override
    public Map<String, JsonFile<?>> getFileMap() {
        return Map.copyOf(jsonFileMap);
    }
}
