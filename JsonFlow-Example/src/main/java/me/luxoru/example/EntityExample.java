package me.luxoru.example;

import me.luxoru.example.entity.impl.Player;
import me.luxoru.jsonflow.core.JsonFlow;

public class EntityExample {

    public static void main(String[] args) {

        System.out.println("Loading player");

        Player player  = null;
        double totalTimeTaken = 0;
        final int interations = 10_000;

        for(int i =0;i<interations;i++){
            long start = System.currentTimeMillis();
            player = JsonFlow.load("player", Player.class, false);
            totalTimeTaken += System.currentTimeMillis() - start;
        }

        System.out.printf("Average Loaded player in %s ms%n", (totalTimeTaken/interations));


        System.out.println(player.toJsonObject().toPrettyString());

        player.move(2,2);
        player.damage(7);

        System.out.println(player.toJsonObject().toPrettyString());

    }

}
