package controller;

// Source code is decompiled from a .class file using FernFlower decompiler.
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Player;

public class PlayerController implements KeyListener {
    protected Player player;
    int speed = 10;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public PlayerController(Player var1) {
        this.player = var1;
    }

    public void keyPressed(KeyEvent var1) {
        if (var1.getKeyCode() == this.player.getPlayerKey(0)) {
            this.leftPressed = true;
        }

        if (var1.getKeyCode() == this.player.getPlayerKey(1)) {
            this.rightPressed = true;
        }

        if (var1.getKeyCode() == this.player.getPlayerKey(2)) {
            this.upPressed = true;
        }

        if (var1.getKeyCode() == this.player.getPlayerKey(3)) {
            this.downPressed = true;
        }

    }

    public void keyReleased(KeyEvent var1) {
        if (var1.getKeyCode() == this.player.getPlayerKey(0)) {
            this.leftPressed = false;
        }

        if (var1.getKeyCode() == this.player.getPlayerKey(1)) {
            this.rightPressed = false;
        }

        if (var1.getKeyCode() == this.player.getPlayerKey(2)) {
            this.upPressed = false;
        }

        if (var1.getKeyCode() == this.player.getPlayerKey(3)) {
            this.downPressed = false;
        }

    }

    public void keyTyped(KeyEvent var1) {
    }

    public void updatePlayer() {
        if (this.leftPressed) {
            this.player.move(-this.speed, 0);
        }

        if (this.rightPressed) {
            this.player.move(this.speed, 0);
        }

        if (this.upPressed) {
            this.player.move(0, -this.speed);
        }

        if (this.downPressed) {
            this.player.move(0, this.speed);
        }

        this.player.playerPosCheck();
    }

    public void resetKeyStates() {
        this.leftPressed = this.rightPressed = this.upPressed = this.downPressed = false;
    }
}
