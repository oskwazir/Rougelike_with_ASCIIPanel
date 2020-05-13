package com.omerwazir;

import com.omerwazir.ai.CreatureAi;

import java.awt.*;

public class Creature {
    private final World world;
    private CreatureAi ai;
    private final char glyph;
    private final Color color;
    private int currentX;
    private int currentY;
    private int currentZ;
    private int healthPoints;
    private final int maxHealthPoints;
    private final int attackPower;
    private final int defensivePower;

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

    public int getCurrentZ() { return this.currentZ; }

    public void setCurrentZ(int newZ) { this.currentZ = newZ; }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getDefenseValue() {
        return defensivePower;
    }


    public Creature(World world,
                    char glyph,
                    Color color,
                    int maxHealthPoints,
                    int attackPower,
                    int defensivePower){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.attackPower = attackPower;
        this.defensivePower = defensivePower;
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

    public void dig(int wx, int wy, int wz) {
        world.dig(wx, wy, wz);
    }

    public void moveBy(int moveX, int moveY, int moveZ) {
        Tile tile = world.tile(currentX +moveX, currentY +moveY, currentZ +moveZ);

        if(moveZ == -1){
            if (tile == Tile.STAIRS_DOWN) {
                doAction("walk up the stairs to level %d", currentZ+moveZ+1);
            } else {
                doAction("try to go up but are stopped by the cave ceiling");

            }
        } else if (moveZ == 1) {
            if (tile == Tile.STAIRS_UP) {
                doAction("walk down the stairs to level %d", currentZ+moveZ+1);

            } else {
                doAction("try to go down but are stopped by the cave floor");
                return;
            }
        }


        Creature other = world.getCreatureAt(currentX + moveX, currentY + moveY, currentZ+moveZ);
        if(other == null) {
            ai.onEnter(currentX + moveX, currentY + moveY, currentZ+moveZ, tile);
        } else {
            attack(other);
        }

    }

    public void update() {
        ai.onUpdate();
    }

    public boolean canEnter(int x, int y, int z) {
        return world.tile(x,y,z).isGround() && world.getCreatureAt(x, y, z) == null;
    }

    public void attack(Creature opponent) {
        int damage = Math.max(0, this.attackPower - opponent.getDefenseValue());

        damage = (int)(Math.random() * damage) + 1;

        opponent.receiveDamage(damage);
        this.doAction("attack the '%s' for %d damage", opponent.glyph, damage);
        this.notify("You attack the '%s' for %d damage.", opponent.glyph, damage);
        opponent.notify("The '%s' attacks you for %d damage.",this.glyph, damage);
    }

    public void receiveDamage(int amount) {
        this.healthPoints -= amount;

        if (this.healthPoints < 1){
            doAction("die");
            world.remove(this);
        }
    }

    public void notify(String message, Object ... params){
        ai.onNotify(String.format(message, params));
    }

    public void doAction(String message, Object ... params){
        int r = 9;
        for (int ox = -r; ox < r+1; ox++){
            for (int oy = -r; oy < r+1; oy++){
                if (ox*ox + oy*oy > r*r)
                    continue;

                Creature other = world.getCreatureAt(currentX+ox, currentY+oy, currentZ);

                if (other == null)
                    continue;

                if (other == this)
                    other.notify("You " + message + ".", params);
                else
                    other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
            }
        }
    }

    private Object makeSecondPerson(String text) {
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words){
            builder.append(" ");
            builder.append(word);
        }

        return builder.toString().trim();
    }
}
