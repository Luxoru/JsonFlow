package me.luxoru.example;

import me.luxoru.example.entity.impl.Player;
import me.luxoru.jsonflow.core.JsonFlow;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class EntityExample {

    public static void main(String[] args) throws URISyntaxException {

        URL resourceUrl = EntityExample.class.getClassLoader().getResource("player.json");

        if (resourceUrl == null) {
            System.out.println("File not found in resources.");
            return;
        }

        File file = Paths.get(resourceUrl.toURI()).toFile();

        Player player = JsonFlow.load(file, Player.class);


        System.out.println(player.toJsonObject().toPrettyString());

        player.damage(2.3f);

        System.out.println(player.toJsonObject().toPrettyString());

    }

}
