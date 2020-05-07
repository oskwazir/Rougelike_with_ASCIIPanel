package com.omerwazir;

import asciiPanel.AsciiPanel;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ApplicationMain extends JFrame implements KeyListener {

    private Screen screen;
    private final AsciiPanel terminal;

    public ApplicationMain(){
        super();
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint(){
        this.terminal.clear();
        this.screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent event){
        this.screen = screen.respondToUserInput(event);
        repaint();
    }

    public void keyReleased(KeyEvent event) { }

    public void keyTyped(KeyEvent event) { }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

    }
}

