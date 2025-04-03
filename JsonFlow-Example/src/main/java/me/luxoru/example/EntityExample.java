package me.luxoru.example;

import com.fasterxml.jackson.databind.node.ObjectNode;
import me.luxoru.example.entity.impl.Player;
import me.luxoru.jsonflow.api.entity.JsonEntity;
import me.luxoru.jsonflow.core.JsonFlow;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class EntityExample {

    public static void main(String[] args) throws URISyntaxException {

        Player player = JsonFlow.load("player", Player.class);

        System.out.println(player.toJsonObject().toPrettyString());

        player.move(2,2);
        player.damage(7);

        System.out.println(player.toJsonObject().toPrettyString());

    }

}
