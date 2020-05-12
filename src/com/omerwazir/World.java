package com.omerwazir;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    private final List<Creature> creatureList = new ArrayList<>();
    private final Tile[][] tiles;
    private final int width;
    private final int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    public Creature getCreatureAt(int x, int y){
        for (Creature creature : creatureList) {
            if (creature.getCurrentX() == x && creature.getCurrentY() == y) return creature;
        }
        return null;
    }

    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height){
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char getGlyph(int x, int y){
        return tile(x, y).getGlyph();
    }

    public Color getColor(int x, int y){
        return tile(x,y).getColor();
    }

    public void dig(int x, int y) {
        if (tile(x,y).isDiggable()) tiles[x][y] = Tile.FLOOR;
    }

    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        } while (!tile(x,y).isGround() || getCreatureAt(x,y) != null);

        creature.setCurrentX(x);
        creature.setCurrentY(y);
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
