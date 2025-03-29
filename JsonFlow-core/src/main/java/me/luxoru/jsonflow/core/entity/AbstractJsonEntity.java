package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.serialize.JsonFlowDeserializer;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;
import me.luxoru.jsonflow.core.util.ReflectionUtilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


@JsonDeserialize(using = AbstractJsonEntityDeserializer.class)
public abstract class AbstractJsonEntity implements JsonEntity {

    private AbstractJsonEntity parent;
    private String fileName;
    private ObjectNode jsonObject;

    protected final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);


    /**
     * Converts all the fields for this JsonEntity to a jsonObject
     * @return JsonObject for this object
     */
    protected ObjectNode thisToJsonObject(){return null;};

    @Override
    public ObjectNode toJsonObject(){

        this.jsonObject = objectMapper.createObjectNode();
        if(this.parent != null){
            this.jsonObject.setAll(this.parent.toJsonObject());
            this.jsonObject.remove("type");
            this.jsonObject.remove("parent");
        }
        if(!this.getClass().isAnnotationPresent(FlowSerializable.class)){
            this.jsonObject.setAll(thisToJsonObject());
            return this.jsonObject;
        }

        List<Field> allFields = ReflectionUtilities.getAllFields(this.getClass());

        for (Field field : allFields) {
            if(!field.isAnnotationPresent(FlowField.class))continue;
            FlowField flowField = field.getAnnotation(FlowField.class);
            Object value = ReflectionUtilities.getField(this, field.getName(), field.getType());

            if(value == null){
                this.jsonObject.put(flowField.fieldName(), "");
                continue;
            }
            Class<? extends JsonFlowDeserializer> serializer = flowField.serializer();

            if (serializer.equals(JsonFlowDeserializer.class)) {

                if (field.getType().isPrimitive() || ReflectionUtilities.isWrapperType(field.getType())) {

                    switch (value) {
                        case Integer number -> this.jsonObject.put(flowField.fieldName(), number);
                        case Double number -> this.jsonObject.put(flowField.fieldName(), number);
                        case Float number -> this.jsonObject.put(flowField.fieldName(), number);
                        case Boolean b -> this.jsonObject.put(flowField.fieldName(), b);
                        default -> {
                            System.out.printf("Unexpected primitive type (%s). Handling as string\n".formatted(field.getType()));
                            this.jsonObject.put(flowField.fieldName(), value.toString());
                        }
                    }
                }
                else if(field.getType().equals(String.class)){
                    this.jsonObject.put(flowField.fieldName(), value.toString());
                }
                else {
                    System.out.printf("Unknown type handled (%s). Does it need a custom handler? Handling as string\n".formatted(field.getType()));
                    this.jsonObject.put(flowField.fieldName(), value.toString());
                }

                continue;
            }

            JsonFlowDeserializer instance = ReflectionUtilities.createInstance(serializer);
            if(instance == null)return null;
            ObjectNode deserialize = instance.deserialize(value);
            this.jsonObject.set(flowField.fieldName(), deserialize);

        }

        return this.jsonObject;

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
