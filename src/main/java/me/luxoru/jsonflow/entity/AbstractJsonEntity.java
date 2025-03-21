package me.luxoru.jsonflow.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.luxoru.jsonflow.serializer.JsonFileSerializer;

public abstract class AbstractJsonEntity implements JsonEntity{

    protected final Gson gson = new GsonBuilder()
            .registerTypeAdapter(this.getClass(), new JsonFileSerializer())
            .setPrettyPrinting()
            .create();


    /**
     * Converts all the fields for this JsonEntity to a jsonObject
     * @return JsonObject for this object
     */
    public abstract JsonObject toJsonObject();



}
