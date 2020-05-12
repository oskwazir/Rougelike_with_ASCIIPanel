package com.omerwazir;

import asciiPanel.AsciiPanel;
import com.omerwazir.ai.FungusAI;
import com.omerwazir.ai.PlayerAi;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newFungus() {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green);
        world.addAtEmptyLocation(fungus);
        new FungusAI(fungus);
        return fungus;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }
}
