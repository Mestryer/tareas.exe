package com.mestryer.pixelsky.app.render;

import com.mestryer.pixelsky.app.engine.GameEngine;
import com.mestryer.pixelsky.core.model.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameRenderer {
    public static final double VIEW_WIDTH = 1280;
    public static final double VIEW_HEIGHT = 720;

    public void render(GraphicsContext g, GameEngine engine) {
        drawBackground(g, engine.getCameraX());
        g.save();
        g.translate(-engine.getCameraX(), 0);
        for (FloatingIsland island : engine.getIslands()) drawIsland(g, island);
        for (Coin coin : engine.getCoins()) drawCoin(g, coin);
        for (Enemy enemy : engine.getEnemies()) if (enemy.isAlive()) drawEnemy(g, enemy);
        drawPlayer(g, engine.getPlayer());
        g.restore();
        drawHud(g, engine);

        if (!engine.isStarted()) overlay(g, "PIXEL SKY JUMP", "ENTER  -  COMENZAR");
        else if (engine.isGameOver()) overlay(g, "GAME OVER", "R  -  REINICIAR NIVEL");
        else if (engine.isComplete()) overlay(g, "NIVEL COMPLETADO", "N  -  SIGUIENTE NIVEL");
    }

    private void drawBackground(GraphicsContext g, double cameraX) {
        g.setFill(Color.rgb(82, 190, 219));
        g.fillRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        for (int i = 0; i < 9; i++) {
            g.setFill(Color.rgb(82 + i * 3, 190 + i * 3, 219 + i * 3));
            g.fillRect(0, 60 + i * 75, VIEW_WIDTH, 80);
        }
        drawCloud(g, 70 - cameraX * 0.08, 540, 1.0);
        drawCloud(g, 820 - cameraX * 0.05, 475, 1.4);
    }

    private void drawIsland(GraphicsContext g, FloatingIsland i) {
        g.setFill(Color.rgb(145, 91, 52));
        g.fillRect(i.getX(), i.getY() + 13, i.getWidth(), i.getHeight() - 13);
        g.setFill(Color.rgb(58, 190, 82));
        g.fillRect(i.getX(), i.getY(), i.getWidth(), 17);
        g.setFill(Color.rgb(38, 150, 67));
        for (double x = i.getX(); x < i.getX() + i.getWidth(); x += 28) g.fillRect(x, i.getY() + 4, 11, 5);
        g.setFill(Color.rgb(104, 63, 39));
        for (double x = i.getX() + 8; x < i.getX() + i.getWidth(); x += 35) g.fillRect(x, i.getY() + 25, 9, 7);
    }

    private void drawCoin(GraphicsContext g, Coin c) {
        if (c.isCollected()) return;
        double bob = Math.sin(System.nanoTime() / 180_000_000.0 + c.getX()) * 4;
        g.setFill(Color.GOLD);
        g.fillRect(c.getX() - 7, c.getY() - 12 + bob, 14, 24);
        g.setFill(Color.rgb(255, 240, 100));
        g.fillRect(c.getX() - 3, c.getY() - 9 + bob, 5, 18);
    }

    private void drawEnemy(GraphicsContext g, Enemy e) {
        g.setFill(Color.rgb(220, 55, 65));
        g.fillRect(e.getX() + 4, e.getY() + 5, 26, 20);
        g.fillRect(e.getX() + 8, e.getY(), 18, 25);
        g.setFill(Color.WHITE);
        g.fillRect(e.getX() + 9, e.getY() + 8, 5, 6);
        g.fillRect(e.getX() + 21, e.getY() + 8, 5, 6);
    }

    private void drawPlayer(GraphicsContext g, Player p) {
        if (p.isInvulnerable() && ((int) (System.nanoTime() / 100_000_000L) % 2 == 0)) return;
        double x = p.getX(), y = p.getY();
        g.setFill(Color.rgb(248, 198, 158)); g.fillRect(x + 10, y + 3, 25, 22);
        g.setFill(Color.rgb(65, 39, 30)); g.fillRect(x + 8, y, 28, 8); g.fillRect(x + 7, y + 6, 8, 17);
        g.setFill(Color.BLACK); g.fillRect(x + 19, y + 11, 4, 6); g.fillRect(x + 29, y + 11, 4, 6);
        g.setFill(Color.WHITE); g.fillRect(x + 9, y + 24, 27, 20); g.fillRect(x + 4, y + 25, 9, 18); g.fillRect(x + 34, y + 25, 9, 18);
        g.setFill(Color.rgb(30, 65, 105)); g.fillRect(x + 11, y + 43, 22, 10); g.fillRect(x + 8, y + 51, 10, 7); g.fillRect(x + 27, y + 51, 10, 7);
        g.setFill(Color.WHITE); g.fillRect(x + 5, y + 56, 14, 5); g.fillRect(x + 26, y + 56, 14, 5);
        g.setFill(Color.RED); g.fillRect(x + 6, y + 60, 13, 3); g.fillRect(x + 27, y + 60, 13, 3);
    }

    private void drawHud(GraphicsContext g, GameEngine e) {
        g.setFill(Color.WHITE); g.setFont(Font.font("Monospaced", 27));
        g.fillText("SCORE: " + e.getPlayer().getScore(), 35, 45);
        g.fillText("LEVEL: " + e.getLevelNumber(), 560, 45);
        for (int i = 0; i < e.getPlayer().getLives(); i++) drawHeart(g, 1110 + i * 43, 25);
        g.setFill(Color.BLACK); g.setFont(Font.font("Monospaced", 14));
        g.fillText("A/D o flechas: MOVER    ESPACIO: SALTAR    F2: CRUD    F9/F10/F11: VENTANA", 25, 700);
    }

    private void drawHeart(GraphicsContext g, double x, double y) {
        g.setFill(Color.RED); g.fillRect(x + 5, y, 12, 5); g.fillRect(x + 25, y, 12, 5);
        g.fillRect(x, y + 5, 42, 18); g.fillRect(x + 5, y + 23, 32, 7); g.fillRect(x + 10, y + 30, 22, 5);
    }

    private void drawCloud(GraphicsContext g, double x, double y, double s) {
        g.setFill(Color.WHITE); g.fillOval(x, y, 75 * s, 42 * s); g.fillOval(x + 45 * s, y - 25 * s, 100 * s, 65 * s); g.fillOval(x + 105 * s, y, 75 * s, 42 * s);
    }

    private void overlay(GraphicsContext g, String title, String subtitle) {
        g.setFill(Color.rgb(0, 0, 0, 0.52)); g.fillRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        g.setFill(Color.WHITE); g.setFont(Font.font("Monospaced", 52)); g.fillText(title, 390, 310);
        g.setFont(Font.font("Monospaced", 21)); g.fillText(subtitle, 430, 365);
    }
}
