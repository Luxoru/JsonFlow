package me.luxoru.jsonflow.core.entity;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.api.annotation.FlowField;
import me.luxoru.jsonflow.api.annotation.FlowSerializable;
import me.luxoru.jsonflow.api.annotation.NodeSerializable;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.api.serialize.JsonNodeConversionHandler;
import me.luxoru.jsonflow.core.serializer.AbstractJsonEntityDeserializer;
import me.luxoru.jsonflow.core.serializer.JsonConverter;
import me.luxoru.jsonflow.core.util.ReflectionUtilities;
import me.luxoru.jsonflow.core.util.databind.FlowObjectMapper;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@JsonDeserialize(using = AbstractJsonEntityDeserializer.class)
public abstract class AbstractJsonEntity implements JsonEntity {

    private Set<AbstractJsonEntity> parents;
    private String fileName;

    protected final FlowObjectMapper objectMapper = (FlowObjectMapper) FlowObjectMapper.instance()
            .enable(SerializationFeature.INDENT_OUTPUT);


    /**
     * Converts all the fields for this JsonEntity to a jsonObject
     * @return JsonObject for this object
     */
    protected ObjectNode thisToJsonObject(){return objectMapper.createObjectNode();};

    @Override
    public ObjectNode toJsonObject(){

        ObjectNode jsonObject = objectMapper.createObjectNode();
        if(this.parents != null){
            for (AbstractJsonEntity parent : this.getParents()) {
                jsonObject.setAll(parent.toJsonObject());
            }

            jsonObject.remove("type");
            jsonObject.remove("parent");
        }
        if(!this.getClass().isAnnotationPresent(FlowSerializable.class)){
            jsonObject.setAll(thisToJsonObject());
            return jsonObject;
        }

        List<Field> allFields = ReflectionUtilities.getAllFieldsReversed(this.getClass(), AbstractJsonEntity.class);

        for (Field field : allFields) {
            
            FlowField flowField = field.getAnnotation(FlowField.class);
            String fieldName = null;
            if(flowField == null){
                fieldName = field.getName();
            }
            else{
                fieldName = (flowField.value().equalsIgnoreCase("") ? field.getName() : flowField.value());
            }

            Object value = ReflectionUtilities.getField(this, field.getName(), field.getType());

            if(value == null){
                jsonObject.putNull(fieldName);
                continue;
            }
            Class<? extends JsonNodeConversionHandler> serializer = flowField == null ? JsonNodeConversionHandler.class : flowField.serializer();

            if (serializer.equals(JsonNodeConversionHandler.class) && !value.getClass().isAnnotationPresent(NodeSerializable.class)) {

                if (field.getType().isPrimitive() || ReflectionUtilities.isWrapperType(field.getType())) {

                    switch (value) {
                        case Integer number -> jsonObject.put(fieldName, number);
                        case Double number -> jsonObject.put(fieldName, number);
                        case Float number -> jsonObject.put(fieldName, number);
                        case Boolean b -> jsonObject.put(fieldName, b);
                        default -> {
                            System.out.printf("Unexpected primitive type (%s). Handling as string\n".formatted(field.getType()));
                            jsonObject.put(fieldName, value.toString());
                        }
                    }
                }
                else if(field.getType().equals(String.class)){
                    jsonObject.put(fieldName, value.toString());
                }
                else {
                    ObjectNode deserialize = JsonConverter.deserialize(value, value.getClass());
                    if(deserialize == null){
                        jsonObject.set(fieldName, objectMapper.valueToTree(value));
                    }
                    else{
                        jsonObject.set(fieldName, deserialize);
                    }

                }

                continue;
            }

            ObjectNode deserialize;
            if(serializer.equals(JsonNodeConversionHandler.class)){
                //No serializer set in field but one in class
                NodeSerializable annotation = value.getClass().getAnnotation(NodeSerializable.class);
                deserialize = JsonConverter.deserialize(value, value.getClass(), annotation.serializer());
            }
            else{
                deserialize = JsonConverter.deserialize(value, value.getClass(), serializer);
            }

            if(deserialize == null)return null;
            jsonObject.set(fieldName, deserialize);

        }

        return jsonObject;

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
