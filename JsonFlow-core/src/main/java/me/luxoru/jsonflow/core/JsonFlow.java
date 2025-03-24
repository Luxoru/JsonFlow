package me.luxoru.jsonflow.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.JsonEntity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonFlow {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T extends AbstractJsonEntity> T load(String fileName, Class<T> clazz){
        return load(new File(fileName), clazz);
    }

    public static <T extends AbstractJsonEntity> T load(File file, Class<T> clazz){
        if(!file.exists())throw new NullPointerException("File doesn't exist");

        try (FileReader reader = new FileReader(file)) {
            return objectMapper.readValue(reader, clazz);
        } catch (IOException ignore) {

        }

        return null;

    }

    public static File save(JsonEntity entity){
        return entity.save();
    }

    public static File save(JsonEntity entity, File loc){
        return entity.save(loc);
    }

}