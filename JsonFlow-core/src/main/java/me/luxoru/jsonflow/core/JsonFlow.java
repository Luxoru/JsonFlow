package me.luxoru.jsonflow.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import me.luxoru.jsonflow.core.entity.JsonEntity;
import me.luxoru.jsonflow.core.file.JsonFile;
import me.luxoru.jsonflow.core.file.manager.AbstractJsonFileManager;

import java.io.File;

public class JsonFlow {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private static final AbstractJsonFileManager manager = new AbstractJsonFileManager();

    public static <T extends JsonEntity<T>> T load(String fileName, Class<T> clazz){
        return load(new File(fileName), clazz);
    }

    @SneakyThrows
    public static <T extends JsonEntity<T>> T load(File file, Class<T> clazz){

        JsonFile<T> jsonFile = manager.readFile(file, clazz, true);
        if(jsonFile == null){
            System.out.println("Couldn't read file: "+file.getName());
            return null;
        }
        return jsonFile.getJsonEntity();



    }

    public static File save(JsonEntity<?> entity){
        return entity.save();
    }

    public static File save(JsonEntity<?> entity, File loc){
        return entity.save(loc);
    }

}