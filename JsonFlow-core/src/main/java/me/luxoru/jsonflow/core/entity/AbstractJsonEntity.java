package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.serialize.JsonFlowConversionHandler;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;
import me.luxoru.jsonflow.core.serializer.JsonConverter;
import me.luxoru.jsonflow.core.util.ReflectionUtilities;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@JsonDeserialize(using = AbstractJsonEntityDeserializer.class)
public abstract class AbstractJsonEntity implements JsonEntity {

    private Set<AbstractJsonEntity> parents;
    private String fileName;
    private ObjectNode jsonObject;

    protected final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);


    /**
     * Converts all the fields for this JsonEntity to a jsonObject
     * @return JsonObject for this object
     */
    protected ObjectNode thisToJsonObject(){return objectMapper.createObjectNode();};

    @Override
    public ObjectNode toJsonObject(){

        this.jsonObject = objectMapper.createObjectNode();
        if(this.parents != null){
            for (AbstractJsonEntity parent : this.getParents()) {
                this.jsonObject.setAll(parent.toJsonObject());
            }

            this.jsonObject.remove("type");
            this.jsonObject.remove("parent");
        }
        if(!this.getClass().isAnnotationPresent(FlowSerializable.class)){
            this.jsonObject.setAll(thisToJsonObject());
            return this.jsonObject;
        }

        List<Field> allFields = ReflectionUtilities.getAllFieldsReversed(this.getClass(), AbstractJsonEntity.class);

        for (Field field : allFields) {
            
            FlowField flowField = field.getAnnotation(FlowField.class);
            String fieldName = null;
            if(flowField == null){
                fieldName = field.getName();
            }
            else{
                fieldName = flowField.value();
            }

            Object value = ReflectionUtilities.getField(this, field.getName(), field.getType());

            if(value == null){
                this.jsonObject.putNull(fieldName);
                continue;
            }
            Class<? extends JsonFlowConversionHandler> serializer = flowField == null ? JsonFlowConversionHandler.class : flowField.serializer();

            if (serializer.equals(JsonFlowConversionHandler.class)) {

                if (field.getType().isPrimitive() || ReflectionUtilities.isWrapperType(field.getType())) {

                    switch (value) {
                        case Integer number -> this.jsonObject.put(fieldName, number);
                        case Double number -> this.jsonObject.put(fieldName, number);
                        case Float number -> this.jsonObject.put(fieldName, number);
                        case Boolean b -> this.jsonObject.put(fieldName, b);
                        default -> {
                            System.out.printf("Unexpected primitive type (%s). Handling as string\n".formatted(field.getType()));
                            this.jsonObject.put(fieldName, value.toString());
                        }
                    }
                }
                else if(field.getType().equals(String.class)){
                    this.jsonObject.put(fieldName, value.toString());
                }
                else {
                    ObjectNode deserialize = JsonConverter.deserialize(value, value.getClass());
                    if(deserialize == null){
                        System.out.printf("Unknown type handled (%s). Does it need a custom handler? Handling as string\n".formatted(field.getType()));
                        this.jsonObject.put(fieldName, value.toString());
                    }
                    else{
                        this.jsonObject.set(fieldName, deserialize);
                    }

                }

                continue;
            }

            ObjectNode deserialize = JsonConverter.deserialize(value, value.getClass(), serializer);
            if(deserialize == null)return null;
            this.jsonObject.set(fieldName, deserialize);

        }

        return this.jsonObject;

    }

    @Override
    public void addParent(JsonEntity parent) {

        if(!(parent instanceof AbstractJsonEntity abstractJsonEntity)){
            throw new IllegalStateException("Parent must be instance of AbstractJsonEntity");
        }

        if(this.parents == null){
            this.parents = new HashSet<>();
        }

        this.parents.add(abstractJsonEntity);
    }

    @Override
    public void addParents(Collection<JsonEntity> entities) {
        for(JsonEntity entity : entities){
            addParent(entity);
        }
    }

    @Override
    public Set<AbstractJsonEntity> getParents() {
        return Set.copyOf(this.parents);
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
