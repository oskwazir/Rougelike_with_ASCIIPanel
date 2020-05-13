package com.omerwazir.ai;

import com.omerwazir.Creature;
import com.omerwazir.Tile;

import java.util.List;

public class PlayerAi extends CreatureAi {
    private final List<String> messages;

    public PlayerAi(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }

    public void onEnter(int x, int y, int z, Tile tile) {
        if (tile.isGround()) {
            creature.setCurrentX(x);
            creature.setCurrentY(y);
            creature.setCurrentZ(z);
        } else if (tile.isDiggable()) {
            creature.dig(x, y, z);
        }
    }

    public void onNotify(String message){
        this.messages.add(message);
    }
}
