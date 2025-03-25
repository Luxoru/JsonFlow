package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.core.file.JsonFile;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;

@JsonDeserialize(using = AbstractJsonEntityDeserializer.class)
public abstract class AbstractJsonEntity<T extends AbstractJsonEntity<T>> implements JsonEntity<T>{

    private AbstractJsonEntity<?> parent;
    private JsonFile<T> jsonFile;

    protected final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);


    /**
     * Converts all the fields for this JsonEntity to a jsonObject
     * @return JsonObject for this object
     */
    protected abstract ObjectNode thisToJsonObject() throws JsonProcessingException;


    public ObjectNode toJsonObject() throws JsonProcessingException{
        ObjectNode node = objectMapper.createObjectNode();
        if(this.parent != null){
            node.setAll(this.parent.toJsonObject());
            node.remove("type");
            node.remove("parent");
        }
        node.setAll(thisToJsonObject());
        return node;

    }

    @Override
    public void setParent(JsonEntity<? extends JsonEntity<?>> parent) {

        if(!(parent instanceof AbstractJsonEntity<? extends AbstractJsonEntity<?>> abstractParent)){
            throw new IllegalStateException("Parent must be an instance of AbstractJsonEntity");
        }
        if(this.parent != null){
            throw new IllegalStateException("Parent already assigned");
        }

        System.out.println("Parent of "+this+" is now "+parent.getJsonFile().getName());

        this.parent = abstractParent;
    }

    @Override
    public AbstractJsonEntity<?> getParent() {
        return this.parent;
    }

    @Override
    public void setJsonFile(JsonFile<T> jsonFile) {
        this.jsonFile = jsonFile;
    }

    @Override
    public JsonFile<T> getJsonFile() {
        return jsonFile;
    }
}
