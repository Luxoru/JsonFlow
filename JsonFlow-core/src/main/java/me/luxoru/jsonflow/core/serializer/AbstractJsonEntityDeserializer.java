package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

import java.io.IOException;

public abstract class AbstractJsonEntityDeserializer extends JsonDeserializer<AbstractJsonEntity> {

    @Override
    public final AbstractJsonEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {



        ObjectNode node = p.getCodec().readTree(p);

        String parent = node.get("parent").asText();
        System.out.println("parent: "+parent);
        // Assuming you have a method to create an AbstractJsonEntity from the node
        AbstractJsonEntity entityData = getEntityData(node);
        entityData.setParent(parent);
        return entityData;
    }

    protected abstract AbstractJsonEntity getEntityData(ObjectNode node);

}
