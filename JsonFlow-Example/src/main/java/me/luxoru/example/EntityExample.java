package me.luxoru.example;

import me.luxoru.example.entity.impl.Player;
import me.luxoru.jsonflow.core.JsonFlow;

import java.io.File;

public class EntityExample {

    public static void main(String[] args) {
        Player player = JsonFlow.load(new File("src/main/resources/player.json"), Player.class);

    }

}
