package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import java.io.IOException;
import java.util.*;


public class RawJsonEntityDeserializer extends EntityDeserializer<RawJsonEntity> {


    @Override
    public RawJsonEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectNode node = p.getCodec().readTree(p);

        List<JsonEntity> jsonEntity = mergeWithParentJson(node);

        LinkedHashMap<String, JsonNode> pairs = new LinkedHashMap<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            pairs.put(key,  value);
        }
        RawJsonEntity entity = new RawJsonEntity(pairs);

        if(jsonEntity != null){
            entity.addParents(jsonEntity);
        }

        return entity;

    }
}
