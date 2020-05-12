package com.omerwazir;

import asciiPanel.AsciiPanel;
import com.omerwazir.ai.FungusAI;
import com.omerwazir.ai.PlayerAi;

import java.util.List;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newFungus() {
        Creature fungus = new Creature(world,
                'f',
                AsciiPanel.green,
                10,
                0,
                0);
        world.addAtEmptyLocation(fungus);
        new FungusAI(fungus, this);
        return fungus;
    }

    public Creature newPlayer(List<String> messages){
        Creature player = new Creature(world,
                '@',
                AsciiPanel.brightWhite,
                100,
                20,
                5);
        world.addAtEmptyLocation(player);
        new PlayerAi(player, messages);
        return player;
    }
}
