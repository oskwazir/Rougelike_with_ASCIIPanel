package com.omerwazir;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    private final List<Creature> creatureList = new ArrayList<>();
    private final Tile[][][] tiles;
    private final int width;
    private final int height;
    private final int depth;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() { return depth; }

    public World(Tile[][][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.depth = tiles[0][0].length;
    }

    public Creature getCreatureAt(int x, int y, int z){
        for (Creature creature : creatureList) {
            if (creature.getCurrentX() == x
                    && creature.getCurrentY() == y
            && creature.getCurrentZ() == z) return creature;
        }
        return null;
    }

    public Tile tile(int x, int y, int z){
        if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth){
            return Tile.BOUNDS;
        } else {
            return tiles[x][y][z];
        }
    }

    public char getGlyph(int x, int y, int z){
        return tile(x, y, z).getGlyph();
    }

    public Color getColor(int x, int y, int z){
        return tile(x,y,z).getColor();
    }

    public void dig(int x, int y, int z) {
        if (tile(x,y,z).isDiggable()) tiles[x][y][z] = Tile.FLOOR;
    }

    public void addAtEmptyLocation(Creature creature, int z) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        } while (!tile(x,y,z).isGround() || getCreatureAt(x,y,z) != null);

        creature.setCurrentX(x);
        creature.setCurrentY(y);
        creature.setCurrentZ(z);

        creatureList.add(creature);
    }

    public void remove(Creature other) {
        creatureList.remove(other);
    }

    public void update(){
        List<Creature> immutableCreatureList = List.copyOf(creatureList);
        for (Creature creature: immutableCreatureList) {
            creature.update();
        }
    }
}
