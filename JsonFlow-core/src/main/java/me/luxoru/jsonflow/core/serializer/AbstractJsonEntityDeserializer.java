package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.core.JsonFlow;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;

import me.luxoru.jsonflow.core.entity.RawJsonEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@RequiredArgsConstructor
public abstract class AbstractJsonEntityDeserializer<T extends AbstractJsonEntity> extends EntityDeserializer<AbstractJsonEntity> {


    @Override
    public final AbstractJsonEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectNode node = p.getCodec().readTree(p);
        JsonEntity jsonEntity = mergeWithParentJson(node);

        T entityData = getEntityData(node);

        if(jsonEntity != null){
            entityData.setParent(jsonEntity);
        }

        return entityData;
    }

    protected abstract T getEntityData(ObjectNode node);

    protected abstract Class<T> getEntityClass();

}
