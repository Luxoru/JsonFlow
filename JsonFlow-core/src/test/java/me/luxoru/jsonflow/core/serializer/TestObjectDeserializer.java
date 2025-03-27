package me.luxoru.jsonflow.core.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import me.luxoru.jsonflow.core.entity.Block;

public class TestObjectDeserializer extends AbstractJsonEntityDeserializer<Block>{
    @SneakyThrows
    @Override
    protected Block getEntityData(ObjectNode node) {

        String texture = node.get("texture").asText();
        int[][] positions = null;
        JsonNode positionsNode = node.get("positions");
        if(positionsNode != null){
            System.out.println("POS: "+node.get("positions"));
            System.out.println("POS ARR: "+positionsNode.isArray());;

            if(positionsNode instanceof ArrayNode arrayNode){
                positions = objectMapper.convertValue(arrayNode, new TypeReference<>() {
                });
            }

        }
        return new Block(texture, positions);
    }

    @Override
    protected Class<Block> getEntityClass() {
        return Block.class;
    }
}
