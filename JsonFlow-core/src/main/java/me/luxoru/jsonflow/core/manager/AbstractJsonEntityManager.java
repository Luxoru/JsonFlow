package me.luxoru.jsonflow.core.manager;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.manager.JsonEntityManager;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;
import me.luxoru.jsonflow.core.serializer.EntityDeserializer;
import me.luxoru.jsonflow.core.serializer.factory.EntityDeserializerFactory;
import me.luxoru.jsonflow.core.util.ReflectionUtilities;
import me.luxoru.jsonflow.core.util.databind.FlowObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class AbstractJsonEntityManager implements JsonEntityManager {

    private final Map<String, JsonEntity> jsonFileMap;
    private final Map<Class<?>, JsonDeserializer<?>> classJsonDeserializerMap;

    private final FlowObjectMapper mapper = FlowObjectMapper.instance();

    public AbstractJsonEntityManager() {
        jsonFileMap = new HashMap<>();
        classJsonDeserializerMap = new HashMap<>();
    }

    @Override
    public JsonEntity getEntity(String name) {
        return jsonFileMap.get(name);
    }

    @Override
    public <T extends JsonEntity> T getEntity(String name, Class<T> clazz) {
        return clazz.cast(jsonFileMap.get(name));
    }

    @Override
    public <T extends JsonEntity> T readFile(String jsonFileName, Class<T> jsonClazz) throws FileNotFoundException {
        return readFile(jsonFileName, jsonClazz, true);
    }

    @Override
    public <T extends JsonEntity> T readFile(String jsonFileName, Class<T> jsonClazz, boolean addFileToCache) throws FileNotFoundException {
        if(!jsonFileName.endsWith(".json")){
            jsonFileName+= ".json";
        }
        URL resourceUrl = ClassLoader.getSystemResource(jsonFileName);

        if (resourceUrl == null) {
            System.out.println("File %s not found in resources.".formatted(jsonFileName));
            return null;
        }

        try {
            File file = Paths.get(resourceUrl.toURI()).toFile();
            return readFile(file, jsonClazz, addFileToCache);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
            T jsonEntity;
            if(!jsonClazz.isAnnotationPresent(JsonDeserialize.class)){
                FlowSerializable serializable = jsonClazz.getAnnotation(FlowSerializable.class);
                JsonDeserializer instance = classJsonDeserializerMap.get(jsonClazz);
                if(instance == null){
                    if(serializable == null || serializable.using().equals(JsonDeserializer.None.class)){
                        instance = EntityDeserializerFactory.createDeserializer(jsonClazz);
                    }
                    else{
                        instance =  ReflectionUtilities.createInstance(serializable.using());
                    }
                    classJsonDeserializerMap.put(jsonClazz, instance);
                }
                JsonParser parser = mapper.getFactory().createParser(reader);
                jsonEntity = (T) instance.deserialize(parser, mapper.getDeserializationContext());
            }
            else{
                jsonEntity = mapper.readValue(reader, jsonClazz);
            }



            if(jsonEntity == null){
                throw new IllegalStateException("Unable to load file %s. Is it being deserialized correctly?".formatted(file.getName()));
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
