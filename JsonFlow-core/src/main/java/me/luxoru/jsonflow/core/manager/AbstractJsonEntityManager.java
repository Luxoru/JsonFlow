package me.luxoru.jsonflow.core.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.manager.JsonFileManager;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AbstractJsonFileManager implements JsonFileManager {

    private final Map<String, JsonEntity<?>> jsonFileMap;

    private final ObjectMapper mapper = new ObjectMapper();

    public AbstractJsonFileManager() {
        jsonFileMap = new HashMap<>();
    }

    @Override
    public <T extends JsonEntity<T>> T readFileRaw(File jsonFile) throws FileNotFoundException {
        return readFile(jsonFile, RawJsonEntity.class);
    }

    @Override
    public RawJsonEntity readFileRaw(File jsonFile, boolean addFileToCache) throws FileNotFoundException {
        return null;
    }

    @Override
    public JsonEntity<?> getFile(String name) {
        return jsonFileMap.get(name);
    }

    @Override
    public <T extends JsonEntity<T>> T readFile(File jsonFile, Class<T> jsonClazz) throws FileNotFoundException {

        return readFile(jsonFile, jsonClazz,true);

    }

    @Override
    public <T extends JsonEntity<T>> T readFile(File file, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException {
        if(!file.exists()){
            throw new FileNotFoundException("File "+file.getName()+" doesnt exist!");
        }

        if(jsonFileMap.containsKey(file.getName())){
            JsonEntity<?> jsonEntity = jsonFileMap.get(file.getName());
            if(jsonClazz.isInstance(jsonEntity)){
                return jsonClazz.cast(jsonEntity);
            }
            throw new IllegalStateException("Type stored is different than type needed?\nWanted: %s\nStored: %s".formatted(jsonClazz.getSimpleName(), jsonEntity.getClass().getSimpleName()));
        }

        try(FileReader reader = new FileReader(file)){

            T jsonEntity = mapper.readValue(reader, jsonClazz);


            if(jsonEntity == null){
                throw new IllegalStateException("SMT DEFO WRONG");
            }

            jsonEntity.setFileName(file.getName());


            if(addFileToCache){
                jsonFileMap.put(jsonEntity.getFileName(), jsonEntity);
            }
            return jsonEntity;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public Collection<JsonEntity<?>> getFiles() {
        return jsonFileMap.values();
    }

    @Override
    public Set<String> getFileNames() {
        return jsonFileMap.keySet();
    }

    @Override
    public Map<String, JsonEntity<?>> getFileMap() {
        return Map.copyOf(jsonFileMap);
    }
}
