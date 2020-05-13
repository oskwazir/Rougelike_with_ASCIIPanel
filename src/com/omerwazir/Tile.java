package com.omerwazir;

import asciiPanel.AsciiPanel;

import java.awt.*;

public enum Tile {
    FLOOR((char)250, AsciiPanel.magenta),
    WALL((char)177, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack),
    STAIRS_DOWN('>', AsciiPanel.white),
    STAIRS_UP('<', AsciiPanel.white);

    private final char glyph;
    private final Color color;

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDiggable() {
        return this == Tile.WALL;
    }

    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }
}
