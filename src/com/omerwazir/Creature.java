package com.omerwazir;

import com.omerwazir.ai.CreatureAi;

import java.awt.*;

public class Creature {
    private World world;
    private CreatureAi ai;
    private final char glyph;
    private final Color color;
    private int currentX;
    private int currentY;

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentX(int newX) {
        this.currentX = newX;
    }

    public void setCurrentY(int newY) {
        this.currentY = newY;
    }

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int moveX, int moveY) {
        Creature other = world.getCreatureAt(currentX + moveX, currentY + moveY);
        if(other == null) {
            ai.onEnter(currentX + moveX,
                    currentY + moveY,
                    world.tile(currentX +moveX, currentY +moveY));
        } else {
            attack(other);
        }

    }

    private void attack(Creature other) {
        world.remove(other);
    }

}
