package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;

@JsonDeserialize(using = AbstractJsonEntityDeserializer.class)
public abstract class AbstractJsonEntity implements JsonEntity{

    private String parent;

    protected final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Converts all the fields for this JsonEntity to a jsonObject
     * @return JsonObject for this object
     */
    protected abstract ObjectNode thisToJsonObject() throws JsonProcessingException;


    public ObjectNode toJsonObject() throws JsonProcessingException{
        ObjectNode node = objectMapper.createObjectNode();
        node.put("parent", this.parent);
        node.setAll(thisToJsonObject());
        return node;

    }

    @Override
    public void setParent(String parent) {
        if(this.parent != null){
            throw new IllegalStateException("Parent already assigned");
        }
        this.parent = parent;
    }

    @Override
    public String getParent() {
        return this.parent;
    }
}
