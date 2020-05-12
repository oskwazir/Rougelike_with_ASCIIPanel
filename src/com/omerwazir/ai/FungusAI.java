package com.omerwazir.ai;

import com.omerwazir.Creature;
import com.omerwazir.CreatureFactory;

public class FungusAI extends CreatureAi{
    private CreatureFactory creatureFactory;
    private int spreadCount;
    private int MAX_SPREAD_COUNT = 5;

    public FungusAI(Creature creature, CreatureFactory creatureFactory) {
        super(creature);
        this.creatureFactory = creatureFactory;
    }


    public void onUpdate(){

        if (spreadCount < MAX_SPREAD_COUNT && Math.random() < 0.02) spread();
    }

    private void spread() {
        int x = creature.getCurrentX() + (int)(Math.random() * 11) - MAX_SPREAD_COUNT;
        int y = creature.getCurrentY() + (int)(Math.random() * 11) - MAX_SPREAD_COUNT;

        if(!creature.canEnter(x,y)) return;

        Creature child = creatureFactory.newFungus();
        child.setCurrentX(x);
        child.setCurrentY(y);
        spreadCount++;

    }

}
