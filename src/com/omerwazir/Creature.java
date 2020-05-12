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

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getAttackValue() {
        return attackPower;
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

    public void update() {
        ai.onUpdate();
    }

    public boolean canEnter(int x, int y) {
        return world.getCreatureAt(x, y) == null;
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

                Creature other = world.getCreatureAt(currentX+ox, currentY+oy);

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
