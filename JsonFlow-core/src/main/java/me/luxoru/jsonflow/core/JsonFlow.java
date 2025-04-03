package me.luxoru.jsonflow.core;

import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.manager.JsonEntityManager;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import me.luxoru.jsonflow.core.manager.AbstractJsonEntityManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public final class JsonFlow {

    private static JsonEntityManager manager;
    private static boolean initialized = false;


    public static void init(){
        manager = new AbstractJsonEntityManager();
        initialized = true;
    }

    public static JsonEntity getEntity(String name){
        return manager.getEntity(name);
    }

    public static <T extends JsonEntity> T getEntity(String name, Class<T> clazz){
        return (T) manager.getEntity(name, clazz);
    }

    public static Collection<JsonEntity> getEntities(){
        return manager.getEntities();
    }

    public static Set<String> getFileNames(){
        return manager.getFileNames();
    }

    public static Map<String, JsonEntity> getFileMap(){
        return manager.getFileMap();
    }

    public static RawJsonEntity loadRaw(File jsonFile){
        return load(jsonFile, RawJsonEntity.class);
    }

    public static RawJsonEntity loadRaw(File jsonFile, boolean addFileToCache){
        return load(jsonFile, RawJsonEntity.class, addFileToCache);
    }

    public static <T extends JsonEntity> T load(String fileName, Class<T> clazz) {
        return load(fileName, clazz, true);
    }

    public static <T extends JsonEntity> T load(String fileName, Class<T> clazz, boolean addFileToCache) {
        if(!initialized){
            init();
        }
        try {
            return manager.readFile(fileName, clazz,addFileToCache);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error occurred loading file");
            return null;
        }
    }

    public static <T extends JsonEntity> T load(File file, Class<T> clazz) {
        return load(file, clazz, true);
    }

    public static <T extends JsonEntity> T load(File jsonFile, Class<T> jsonClazz, boolean addFileToCache){
        if(!initialized){
            init();
        }
        try {
            return manager.readFile(jsonFile, jsonClazz,addFileToCache);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error occurred loading file");
            return null;
        }
    }

}