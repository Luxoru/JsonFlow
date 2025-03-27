package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import me.luxoru.jsonflow.core.manager.AbstractJsonEntityManager;
import me.luxoru.jsonflow.api.manager.JsonEntityManager;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractJsonEntityDeserializer<T extends AbstractJsonEntity> extends JsonDeserializer<AbstractJsonEntity> {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    private final JsonEntityManager fileManager;

    public AbstractJsonEntityDeserializer(){
        this.fileManager = new AbstractJsonEntityManager();
    }

    @Override
    public final AbstractJsonEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

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
            jsonEntity = fileManager.readFile(new File(parent), RawJsonEntity.class);
        }

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

        T entityData = getEntityData(node);

        if(jsonEntity != null){
            entityData.setParent(jsonEntity);
        }

        return entityData;
    }

    protected abstract T getEntityData(ObjectNode node);

    protected abstract Class<T> getEntityClass();

}
