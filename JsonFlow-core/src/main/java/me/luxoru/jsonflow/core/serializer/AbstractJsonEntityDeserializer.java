package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.JsonEntity;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;
import me.luxoru.jsonflow.core.file.JsonFile;
import me.luxoru.jsonflow.core.file.manager.AbstractJsonFileManager;
import me.luxoru.jsonflow.core.file.manager.JsonFileManager;

import java.io.File;
import java.io.IOException;

public abstract class AbstractJsonEntityDeserializer<T extends AbstractJsonEntity<T>> extends JsonDeserializer<AbstractJsonEntity<T>> {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final JsonFileManager fileManager;

    public AbstractJsonEntityDeserializer(){
        this.fileManager = new AbstractJsonFileManager();
    }

    public AbstractJsonEntityDeserializer(JsonFileManager manager){
        this.fileManager = manager;
    }

    @Override
    public final AbstractJsonEntity<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {


        ObjectNode node = p.getCodec().readTree(p);
        String parent = null;

        if(node.get("parent") != null){
            parent = node.get("parent").asText();
            System.out.println(parent);;

            if(!parent.endsWith(".json")){
                parent += ".json";
            }
        }




        //TODO: Find parent json and deserialise


        T entityData = getEntityData(node);

        System.out.println(entityData);

        if(parent != null){
            JsonFile<RawJsonEntity> abstractJsonEntityJsonFile = fileManager.readFile(new File(parent), RawJsonEntity.class);
            RawJsonEntity jsonEntity = abstractJsonEntityJsonFile.getJsonEntity();

            entityData.setParent(jsonEntity);
        }



        //entityData.setParent(parent);
        return entityData;
    }

    protected abstract T getEntityData(ObjectNode node);

    protected abstract Class<T> getEntityClass();

}
