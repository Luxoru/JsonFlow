package me.luxoru.example;

import me.luxoru.example.entity.impl.Player;
import me.luxoru.jsonflow.api.entity.JsonEntity;
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
        JsonEntity entity = JsonFlow.getEntity(file.getName(), Player.class);

        player.damage(2.3f);

        player.move(-2.2f, 3.2f);

    }

}
