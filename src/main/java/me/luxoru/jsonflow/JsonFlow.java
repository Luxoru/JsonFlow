package me.luxoru.jsonflow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.luxoru.jsonflow.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.entity.JsonEntity;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonFlow {

    public static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    public static <T> T load(String fileName, Class<T> clazz){
        return load(new File(fileName), clazz);
    }

    public static <T extends AbstractJsonEntity> T load(File file, Class<T> clazz){
        if(!file.exists())throw new NullPointerException("File doesn't exist");

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
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