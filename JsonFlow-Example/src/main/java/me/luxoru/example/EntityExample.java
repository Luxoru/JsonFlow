package me.luxoru.example;

import me.luxoru.example.entity.impl.Player;
import me.luxoru.jsonflow.core.JsonFlow;

public class EntityExample {

    public static void main(String[] args) {

        Player player = JsonFlow.load("player", Player.class);

        String json = player.toJsonObject().toPrettyString(); //Takes fields from POJO and converts to json

        System.out.println(json);

        player.move(2,2);

        System.out.println(player.getPosition());



    }

}
