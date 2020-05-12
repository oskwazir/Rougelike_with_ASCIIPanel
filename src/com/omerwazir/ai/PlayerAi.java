package com.omerwazir.ai;

import com.omerwazir.Creature;
import com.omerwazir.Tile;

public class PlayerAi extends CreatureAi {
    public PlayerAi(Creature creature) {
        super(creature);
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setCurrentX(x);
            creature.setCurrentY(y);
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        }
    }
}
