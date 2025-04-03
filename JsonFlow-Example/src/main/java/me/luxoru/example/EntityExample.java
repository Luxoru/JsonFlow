package me.luxoru.example;

import me.luxoru.example.entity.impl.Player;
import me.luxoru.jsonflow.core.JsonFlow;
import java.net.URISyntaxException;

public class EntityExample {

    public static void main(String[] args) {

        Player player = JsonFlow.load("player", Player.class);

        System.out.println(player.toJsonObject().toPrettyString());

        player.move(2,2);
        player.damage(7);

        System.out.println(player.toJsonObject().toPrettyString());

    }

}
