package me.luxoru.jsonflow.core;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import me.luxoru.jsonflow.core.entity.AbstractJsonEntity;
import me.luxoru.jsonflow.core.entity.Block;
import org.junit.jupiter.api.Test;

import java.io.File;

public class JsonFlowTest {

    @SneakyThrows
    @Test
    public void test(){
        Block obj = JsonFlow.load(new File("src/test/resources/grass_block.json"), Block.class);
        System.out.println(obj);

        if(obj == null)return;

        String name = obj.getJsonFile().getName();

        System.out.println("FILE_NAME: "+name);
        System.out.println("TEXTURE: "+obj.getTexture());

        ObjectNode jsonObject = obj.toJsonObject();
        System.out.println(jsonObject);
        System.out.println(obj.getParent());
    }

}
