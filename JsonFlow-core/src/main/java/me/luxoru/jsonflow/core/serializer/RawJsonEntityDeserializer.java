package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import me.luxoru.jsonflow.core.manager.AbstractJsonEntityManager;
import me.luxoru.jsonflow.api.manager.JsonEntityManager;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RawJsonEntityDeserializer extends JsonDeserializer<RawJsonEntity> {

    private final JsonEntityManager fileManager;

    public RawJsonEntityDeserializer(){
        this.fileManager = new AbstractJsonEntityManager();
    }


    @Override
    public RawJsonEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectNode node = p.getCodec().readTree(p);

        String parent = null;

        if(node.get("parent") != null){
            parent = node.get("parent").asText();

            if(!parent.endsWith(".json")){
                parent += ".json";
            }
        }
        RawJsonEntity jsonEntity = null;
        if(parent != null){
            URL url = AbstractJsonEntityDeserializer.class.getClassLoader().getResource(parent);

            if(url != null){

                try {
                    jsonEntity = fileManager.readFile(Paths.get(url.toURI()).toFile(), RawJsonEntity.class);
                } catch (URISyntaxException _) {
                }
            }


        }

        LinkedHashMap<String, JsonNode> pairs = new LinkedHashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            pairs.put(key,  value);
        }
        RawJsonEntity entity = new RawJsonEntity(pairs);

        //Squash tree
        if(jsonEntity != null){
            ObjectNode jsonObject = jsonEntity.toJsonObject();
            jsonObject.fields().forEachRemaining(entry ->{
                if(!node.has(entry.getKey())){
                    node.set(entry.getKey(), entry.getValue());
                }
            });
            node.remove("parent");
            node.remove("type");

        }

        if(jsonEntity != null){
            entity.setParent(jsonEntity);
        }

        return entity;

    }
}
