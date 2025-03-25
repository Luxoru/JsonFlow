package me.luxoru.jsonflow.core;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.Block;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

public class JsonFlowTest {

    @SneakyThrows
    @Test
    public void test(){
        var obj = JsonFlow.load(new File("src/test/resources/grass_block.json"), Block.class);
        System.out.println(obj);

        if(obj == null)return;

        String name = obj.getJsonFile().getName();



        ObjectNode jsonObject = obj.toJsonObject();

        System.out.println(jsonObject.toPrettyString());

        System.out.println(Arrays.deepToString(obj.getPositions()));
        System.out.println(obj.getTexture());
        System.out.println(obj);

    }

}
