package com.omerwazir.ai;

import com.omerwazir.Creature;
import com.omerwazir.Tile;

public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public void onEnter(int x, int y, int z, Tile tile) {
    }

    public void onUpdate() {
    }

    public void onNotify(String format) {
    }
}
