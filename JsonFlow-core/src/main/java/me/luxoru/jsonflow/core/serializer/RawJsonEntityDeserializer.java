package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import me.luxoru.jsonflow.core.file.JsonFile;
import me.luxoru.jsonflow.core.file.manager.AbstractJsonFileManager;
import me.luxoru.jsonflow.core.file.manager.JsonFileManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RawJsonEntityDeserializer extends JsonDeserializer<RawJsonEntity> {

    private final JsonFileManager fileManager;

    public RawJsonEntityDeserializer(){
        this.fileManager = new AbstractJsonFileManager();
    }

    public RawJsonEntityDeserializer(JsonFileManager manager){
        this.fileManager = manager;
    }

    @Override
    public RawJsonEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        ObjectNode node = p.getCodec().readTree(p);



        Map<String, String> pairs = new HashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            System.out.println(value);
            pairs.put(key,  value.toPrettyString());
        }
        RawJsonEntity entity = new RawJsonEntity(pairs);

        String parent = null;

        if(node.get("parent") != null){
            parent = node.get("parent").asText();
            System.out.println(parent);;

            if(!parent.endsWith(".json")){
                parent += ".json";
            }
        }

        if(parent != null){
            JsonFile<RawJsonEntity> abstractJsonEntityJsonFile = fileManager.readFile(new File(parent), RawJsonEntity.class);
            RawJsonEntity jsonEntity = abstractJsonEntityJsonFile.getJsonEntity();

            entity.setParent(jsonEntity);
        }


        return entity;

    }
}
