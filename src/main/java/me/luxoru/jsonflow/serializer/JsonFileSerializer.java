package me.luxoru.jsonflow.serializer;

import com.google.gson.*;
import me.luxoru.jsonflow.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.entity.JsonEntity;

import java.lang.reflect.Type;

public class JsonFileSerializer implements JsonSerializer<AbstractJsonEntity>, JsonDeserializer<AbstractJsonEntity> {

    @Override
    public JsonElement serialize(AbstractJsonEntity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("parent", src.getParent());

        object.add("data", src.toJsonObject());

        return object;

    }

    @Override
    public AbstractJsonEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {



    }
}
