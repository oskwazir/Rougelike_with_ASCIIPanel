package com.omerwazir;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {

    private static final int MAX_CREATURES = 8;
    private final int screenWidth;
    private final int screenHeight;
    private World world;
    private Creature player;
    private List<String> messages;

    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 21;
        messages = new ArrayList<>();
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory);
    }

    private void createCreatures(CreatureFactory creatureFactory) {
        player = creatureFactory.newPlayer(messages);

        for (int i = 0; i < MAX_CREATURES; i++) {
            creatureFactory.newFungus();
        }
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages){
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++){
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }

    private void createWorld() {
        world = new WorldBuilder(90, 31).makeCaves().build();
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.getCurrentX() - screenWidth / 2, world.getWidth() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.getCurrentY() - screenHeight / 2, world.getHeight() - screenHeight));
    }

    /*
        TODO Fix displaying creatures on map

        http://trystans.blogspot.com/2011/09/roguelike-tutorial-05-stationary.html
        This is actually a very inefficient way to do this.
        It would be far better to draw all the tiles and then, for each creature,
        draw it if it is in the viewable region of left to left+screenWidth and top to top+screenHeight.
        That way we loop through screenWidth * screenHeight tiles + the number of creatures.
     */
    public void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                Creature creature = world.getCreatureAt(wx, wy);
                if (creature != null) {
                    terminal.write(creature.getGlyph(),
                            creature.getCurrentX() - left,
                            creature.getCurrentY() - top,
                            creature.getColor());
                } else {
                    terminal.write(world.getGlyph(wx, wy), x, y, world.getColor(wx, wy));
                }
            }
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();
        String stats = String.format(" %3d/%3d hp", player.getHealthPoints(), player.getMaxHealthPoints());
        displayTiles(terminal, left, top);
        terminal.write(player.getGlyph(),
                player.getCurrentX() - left,
                player.getCurrentY() - top,
                player.getColor());
        terminal.write(stats, 1, 23);
        this.displayMessages(terminal, this.messages);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_Y:
                player.moveBy(-1, -1);
                break;
            case KeyEvent.VK_U:
                player.moveBy(1, -1);
                break;
            case KeyEvent.VK_B:
                player.moveBy(-1, 1);
                break;
            case KeyEvent.VK_N:
                player.moveBy(1, 1);
                break;
        }
        world.update();
        return this;
    }
}
