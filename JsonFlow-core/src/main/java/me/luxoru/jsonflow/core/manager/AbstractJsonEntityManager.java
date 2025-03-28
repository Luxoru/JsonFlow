package me.luxoru.jsonflow.core.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.manager.JsonEntityManager;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AbstractJsonEntityManager implements JsonEntityManager {

    private final Map<String, JsonEntity> jsonFileMap;

    private final ObjectMapper mapper = new ObjectMapper();

    public AbstractJsonEntityManager() {
        jsonFileMap = new HashMap<>();
    }

    @Override
    public JsonEntity getEntity(String name) {
        return jsonFileMap.get(name);
    }

    @Override
    public <T extends JsonEntity> JsonEntity getEntity(String name, Class<T> clazz) {
        return clazz.cast(jsonFileMap.get(name));
    }

    @Override
    public <T extends JsonEntity> T readFile(File jsonFile, Class<T> jsonClazz) throws FileNotFoundException {
        return readFile(jsonFile, jsonClazz,true);
    }

    @Override
    public <T extends JsonEntity> T readFile(File file, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException {
        if(!file.exists()){
            throw new FileNotFoundException("File "+file.getAbsoluteFile()+" doesnt exist!");
        }

        if(jsonFileMap.containsKey(file.getName())){
            JsonEntity jsonEntity = jsonFileMap.get(file.getName());
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
    public Collection<JsonEntity> getEntities() {
        return jsonFileMap.values();
    }

    @Override
    public Set<String> getFileNames() {
        return jsonFileMap.keySet();
    }

    @Override
    public Map<String, JsonEntity> getFileMap() {
        return Map.copyOf(jsonFileMap);
    }
}
