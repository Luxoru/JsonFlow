package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;
import me.luxoru.jsonflow.core.util.ReflectionUtilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@JsonDeserialize(using = AbstractJsonEntityDeserializer.class)
public abstract class AbstractJsonEntity implements JsonEntity {

    private AbstractJsonEntity parent;
    private String fileName;


    protected final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);


    /**
     * Converts all the fields for this JsonEntity to a jsonObject
     * @return JsonObject for this object
     */
    protected ObjectNode thisToJsonObject(){return objectMapper.createObjectNode();};

    @Override
    public ObjectNode toJsonObject(){

        ObjectNode node = objectMapper.createObjectNode();
        if(this.parent != null){
            node.setAll(this.parent.toJsonObject());
            node.remove("type");
            node.remove("parent");
        }
        if(!this.getClass().isAnnotationPresent(FlowSerializable.class)){
            System.out.println(this.getClass());
            node.setAll(thisToJsonObject());
            return node;
        }

        for (Field field : this.getClass().getDeclaredFields()) {
            if(!field.isAnnotationPresent(FlowField.class))continue;
            FlowField flowField = field.getAnnotation(FlowField.class);
            Object value = ReflectionUtilities.getField(this, field.getName(), field.getType());
            if(value == null)continue;
            node.put(flowField.fieldName(), value.toString());
        }


        return node;

    }

    @Override
    public void setParent(JsonEntity parent) {

        if(!(parent instanceof AbstractJsonEntity abstractJsonEntity)){
            throw new IllegalStateException("Parent must be instance of AbstractJsonEntity");
        }

        if(this.parent != null){
            throw new IllegalStateException("Parent already assigned");
        }

        this.parent = abstractJsonEntity;
    }

    @Override
    public AbstractJsonEntity getParent() {
        return this.parent;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
