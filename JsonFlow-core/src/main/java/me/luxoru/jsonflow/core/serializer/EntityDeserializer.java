package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.core.JsonFlow;
import me.luxoru.jsonflow.core.entity.RawJsonEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class EntityDeserializer<T> extends JsonDeserializer<T> {

    @Getter
    private final Class<T> entityClass;

    protected EntityDeserializer() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
            this.entityClass = (Class<T>) actualType; // Cast to Class<T>
        } else {
            throw new RuntimeException("Could not determine generic type.");
        }
    }

    protected List<JsonEntity> mergeWithParentJson(ObjectNode rootNode){

        JsonNode parentNode = rootNode.get("parent");

        if(parentNode == null)return null;

        List<JsonEntity> entities = new ArrayList<>();

        if(parentNode.isArray()){
            for(JsonNode node : parentNode){
                if(node.isArray())throw new IllegalStateException("Inner arrays as parents not supported!");

                String parent = node.asText();

                if(!parent.endsWith(".json")){
                    parent += ".json";
                }

                RawJsonEntity jsonEntity = null;
                if(parent != null) {
                    URL url = AbstractJsonEntityDeserializer.class.getClassLoader().getResource(parent);

                    if (url != null) {

                        try {
                            jsonEntity = JsonFlow.load(Paths.get(url.toURI()).toFile(), RawJsonEntity.class);
                        } catch (URISyntaxException _) {
                        }
                    }


                }

                //Squash tree
                if(jsonEntity != null){
                    ObjectNode jsonObject = jsonEntity.toJsonObject();
                    jsonObject.fields().forEachRemaining(entry ->{
                        if(!rootNode.has(entry.getKey())){
                            rootNode.set(entry.getKey(), entry.getValue());
                        }
                    });
                    rootNode.remove("parent");
                    rootNode.remove("type");
                    entities.add(jsonEntity);
                }




            }
            return entities;
        }

        //1 value in parent;

        String parent = parentNode.asText();

        if(!parent.endsWith(".json")){
            parent += ".json";
        }

        RawJsonEntity jsonEntity = null;
        if(parent != null) {
            URL url = AbstractJsonEntityDeserializer.class.getClassLoader().getResource(parent);

            if (url != null) {

                try {
                    jsonEntity = JsonFlow.load(Paths.get(url.toURI()).toFile(), RawJsonEntity.class);
                } catch (URISyntaxException _) {
                }
            }


        }

        //Squash tree
        if(jsonEntity != null){
            ObjectNode jsonObject = jsonEntity.toJsonObject();
            jsonObject.fields().forEachRemaining(entry ->{
                if(!rootNode.has(entry.getKey())){
                    rootNode.set(entry.getKey(), entry.getValue());
                }
            });
            rootNode.remove("parent");
            rootNode.remove("type");
            entities.add(jsonEntity);
        }

        return entities;


    }

}
