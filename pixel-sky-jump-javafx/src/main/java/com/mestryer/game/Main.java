package com.mestryer.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private static final int W = 960;
    private static final int H = 540;

    private Canvas canvas;
    private GraphicsContext g;
    private final Set<KeyCode> keys = new HashSet<>();
    private final List<Platform> platforms = new ArrayList<>();
    private final List<Coin> coins = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();

    private Player player;
    private int score = 1996;
    private int lives = 3;
    private boolean started = false;
    private boolean gameOver = false;
    private long lastTime;
    private double worldTime = 0;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(W, H);
        g = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, W, H);

        scene.setOnKeyPressed(e -> {
            keys.add(e.getCode());

            if (!started && e.getCode() == KeyCode.ENTER) {
                startGame();
            } else if (gameOver && e.getCode() == KeyCode.R) {
                resetGame();
            }
        });

        scene.setOnKeyReleased(e -> keys.remove(e.getCode()));

        buildLevel();
        drawTitle();

        stage.setTitle("Pixel Sky Jump - JavaFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        lastTime = System.nanoTime();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double dt = Math.min((now - lastTime) / 1_000_000_000.0, 0.033);
                lastTime = now;

                worldTime += dt;

                if (started && !gameOver) {
                    update(dt);
                }

                draw();
            }
        };
        timer.start();
    }

    private void buildLevel() {
        platforms.clear();
        coins.clear();
        enemies.clear();

        // Plataformas flotantes inspiradas en la composición de la imagen.
        platforms.add(new Platform(70, 400, 230, 42));
        platforms.add(new Platform(325, 345, 185, 42));
        platforms.add(new Platform(585, 405, 220, 42));
        platforms.add(new Platform(760, 305, 145, 42));
        platforms.add(new Platform(470, 220, 145, 42));
        platforms.add(new Platform(150, 205, 170, 42));

        coins.add(new Coin(170, 360));
        coins.add(new Coin(390, 305));
        coins.add(new Coin(650, 365));
        coins.add(new Coin(815, 265));
        coins.add(new Coin(530, 180));
        coins.add(new Coin(235, 165));

        enemies.add(new Enemy(120, 365, 70, 300));
        enemies.add(new Enemy(620, 370, 585, 780));
    }

    private void startGame() {
        started = true;
        gameOver = false;
        player = new Player(120, 330);
    }

    private void resetGame() {
        score = 1996;
        lives = 3;
        buildLevel();
        startGame();
    }

    private void update(double dt) {
        player.update(dt);

        for (Enemy e : enemies) {
            e.update(dt);
        }

        // Colisión jugador-plataformas al caer.
        player.onGround = false;
        for (Platform p : platforms) {
            if (player.vy >= 0 &&
                player.x + player.w > p.x &&
                player.x < p.x + p.w &&
                player.y + player.h <= p.y + 12 &&
                player.y + player.h + player.vy * dt >= p.y) {

                player.y = p.y - player.h;
                player.vy = 0;
                player.onGround = true;
            }
        }

        // Monedas.
        for (Coin c : coins) {
            if (!c.collected && intersects(player.x, player.y, player.w, player.h,
                                            c.x - 10, c.y - 10, 20, 20)) {
                c.collected = true;
                score += 100;
            }
        }

        // Enemigos.
        for (Enemy e : enemies) {
            if (intersects(player.x + 4, player.y + 4, player.w - 8, player.h - 8,
                           e.x, e.y, e.w, e.h)) {
                // Si cae encima del enemigo, lo derrota.
                if (player.vy > 0 && player.y + player.h - e.y < 18) {
                    e.alive = false;
                    player.vy = -420;
                    score += 250;
                } else if (e.alive && player.invulnerability <= 0) {
                    loseLife();
                    return;
                }
            }
        }

        enemies.removeIf(e -> !e.alive);

        // Caída fuera del escenario.
        if (player.y > H + 80) {
            loseLife();
        }

        // Límites laterales.
        player.x = Math.max(10, Math.min(W - player.w - 10, player.x));

        // Victoria al recoger todas las monedas.
        if (coins.stream().allMatch(c -> c.collected)) {
            score += 500;
            for (Coin c : coins) c.collected = false;
            player.vy = -500;
        }
    }

    private void loseLife() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
            return;
        }
        player.x = 120;
        player.y = 300;
        player.vx = 0;
        player.vy = 0;
        player.invulnerability = 2.0;
    }

    private boolean intersects(double ax, double ay, double aw, double ah,
                               double bx, double by, double bw, double bh) {
        return ax < bx + bw && ax + aw > bx && ay < by + bh && ay + ah > by;
    }

    private void drawTitle() {
        drawBackground();
        drawPixelText("PIXEL SKY JUMP", 300, 180, 44, Color.WHITE);
        drawPixelText("JAVA FX EDITION", 355, 225, 24, Color.BLACK);
        drawPixelText("ENTER  •  COMENZAR", 330, 335, 22, Color.WHITE);
        drawPixelText("← → / A D = MOVER     ESPACIO = SALTAR", 230, 385, 17, Color.BLACK);

        drawCloud(60, 430, 1.0);
        drawCloud(730, 425, 1.2);
    }

    private void draw() {
        if (!started) {
            drawTitle();
            return;
        }

        drawBackground();

        for (Platform p : platforms) p.draw(g);
        for (Coin c : coins) c.draw(g);
        for (Enemy e : enemies) e.draw(g);
        player.draw(g);

        drawHUD();

        if (gameOver) {
            g.setFill(Color.rgb(0, 0, 0, 0.55));
            g.fillRect(0, 0, W, H);
            drawPixelText("GAME OVER", 335, 235, 48, Color.WHITE);
            drawPixelText("PUNTAJE: " + score, 365, 285, 22, Color.WHITE);
            drawPixelText("PULSA R PARA REINICIAR", 305, 335, 20, Color.WHITE);
        }
    }

    private void drawBackground() {
        g.setFill(Color.rgb(82, 190, 219));
        g.fillRect(0, 0, W, H);

        // Degradado visual por franjas.
        for (int i = 0; i < 9; i++) {
            g.setFill(Color.rgb(82 + i * 3, 190 + i * 3, 219 + i * 3));
            g.fillRect(0, 65 + i * 55, W, 55);
        }

        drawCloud(15, 475, 0.8);
        drawCloud(760, 465, 1.35);
        drawCloud(420, 500, 0.55);
    }

    private void drawHUD() {
        drawPixelText("SCORE: " + score, 45, 62, 30, Color.WHITE);

        for (int i = 0; i < 3; i++) {
            double x = 820 + i * 42;
            if (i < lives) drawHeart(x, 45);
        }

        drawPixelText("A/D o ←/→  MOVER", 40, 515, 14, Color.BLACK);
        drawPixelText("ESPACIO  SALTAR", 760, 515, 14, Color.BLACK);
    }

    private void drawHeart(double x, double y) {
        g.setFill(Color.RED);
        g.fillRect(x + 5, y, 12, 5);
        g.fillRect(x + 25, y, 12, 5);
        g.fillRect(x, y + 5, 42, 18);
        g.fillRect(x + 5, y + 23, 32, 7);
        g.fillRect(x + 10, y + 30, 22, 5);
    }

    private void drawCloud(double x, double y, double s) {
        g.setFill(Color.WHITE);
        g.fillOval(x, y, 75 * s, 42 * s);
        g.fillOval(x + 45 * s, y - 25 * s, 100 * s, 65 * s);
        g.fillOval(x + 105 * s, y, 75 * s, 42 * s);

        g.setFill(Color.rgb(242, 192, 239));
        g.fillRect(x + 35 * s, y + 28 * s, 12 * s, 6 * s);
        g.fillRect(x + 80 * s, y + 12 * s, 9 * s, 6 * s);
        g.fillRect(x + 125 * s, y + 29 * s, 15 * s, 6 * s);
    }

    private void drawPixelText(String text, double x, double y, double size, Color color) {
        g.setFont(Font.font("Monospaced", size));
        g.setFill(color);
        g.fillText(text, x, y);
    }

    // ---------- CLASES DEL JUEGO ----------

    static class Player {
        double x, y, vx, vy;
        final double w = 42, h = 58;
        boolean onGround = false;
        double invulnerability = 0;

        Player(double x, double y) {
            this.x = x;
            this.y = y;
        }

        void update(double dt) {
            boolean left = MainHolder.keys.contains(KeyCode.LEFT) || MainHolder.keys.contains(KeyCode.A);
            boolean right = MainHolder.keys.contains(KeyCode.RIGHT) || MainHolder.keys.contains(KeyCode.D);

            if (left) vx -= 900 * dt;
            if (right) vx += 900 * dt;
            if (!left && !right) vx *= Math.pow(0.001, dt);

            vx = Math.max(-250, Math.min(250, vx));

            boolean jump = MainHolder.keys.contains(KeyCode.SPACE)
                    || MainHolder.keys.contains(KeyCode.UP)
                    || MainHolder.keys.contains(KeyCode.W);

            if (jump && onGround) {
                vy = -570;
                onGround = false;
            }

            vy += 1300 * dt;
            x += vx * dt;
            y += vy * dt;

            if (invulnerability > 0) invulnerability -= dt;
        }

        void draw(GraphicsContext g) {
            if (invulnerability > 0 && ((int)(invulnerability * 12) % 2 == 0)) return;

            // Cabeza
            g.setFill(Color.rgb(248, 198, 158));
            g.fillRect(x + 10, y + 3, 25, 22);

            // Pelo
            g.setFill(Color.rgb(65, 39, 30));
            g.fillRect(x + 8, y, 28, 8);
            g.fillRect(x + 7, y + 6, 8, 17);

            // Cara
            g.setFill(Color.BLACK);
            g.fillRect(x + 19, y + 11, 4, 6);
            g.fillRect(x + 29, y + 11, 4, 6);

            // Camiseta
            g.setFill(Color.WHITE);
            g.fillRect(x + 9, y + 24, 27, 20);
            g.fillRect(x + 4, y + 25, 9, 18);
            g.fillRect(x + 34, y + 25, 9, 18);

            // Pantalón
            g.setFill(Color.rgb(30, 65, 105));
            g.fillRect(x + 11, y + 43, 22, 10);
            g.fillRect(x + 8, y + 51, 10, 7);
            g.fillRect(x + 27, y + 51, 10, 7);

            // Zapatillas
            g.setFill(Color.WHITE);
            g.fillRect(x + 5, y + 56, 14, 5);
            g.fillRect(x + 26, y + 56, 14, 5);
            g.setFill(Color.RED);
            g.fillRect(x + 6, y + 60, 13, 3);
            g.fillRect(x + 27, y + 60, 13, 3);
        }
    }

    static class Platform {
        double x, y, w, h;

        Platform(double x, double y, double w, double h) {
            this.x = x; this.y = y; this.w = w; this.h = h;
        }

        void draw(GraphicsContext g) {
            // Tierra
            g.setFill(Color.rgb(145, 91, 52));
            g.fillRect(x, y + 12, w, h - 12);

            // Pasto
            g.setFill(Color.rgb(58, 190, 82));
            g.fillRect(x, y, w, 16);

            // Detalles pixel
            g.setFill(Color.rgb(38, 150, 67));
            for (int i = 0; i < w; i += 27) {
                g.fillRect(x + i, y + 4, 10, 5);
            }

            g.setFill(Color.rgb(105, 63, 39));
            for (int i = 8; i < w; i += 34) {
                g.fillRect(x + i, y + 23, 8, 7);
            }

            g.setFill(Color.rgb(95, 155, 82));
            for (int i = 18; i < w; i += 48) {
                g.fillRect(x + i, y + 34, 7, 5);
            }
        }
    }

    static class Coin {
        double x, y;
        boolean collected = false;

        Coin(double x, double y) {
            this.x = x; this.y = y;
        }

        void draw(GraphicsContext g) {
            if (collected) return;
            double bob = Math.sin(System.nanoTime() / 180_000_000.0 + x) * 4;

            g.setFill(Color.GOLD);
            g.fillRect(x - 7, y - 12 + bob, 14, 24);
            g.setFill(Color.rgb(255, 240, 100));
            g.fillRect(x - 3, y - 9 + bob, 5, 18);
            g.setFill(Color.ORANGE);
            g.fillRect(x - 9, y - 7 + bob, 3, 14);
            g.fillRect(x + 6, y - 7 + bob, 3, 14);
        }
    }

    static class Enemy {
        double x, y, left, right, speed = 80;
        final double w = 34, h = 25;
        boolean alive = true;

        Enemy(double x, double y, double left, double right) {
            this.x = x; this.y = y; this.left = left; this.right = right;
        }

        void update(double dt) {
            x += speed * dt;
            if (x < left || x + w > right) {
                speed *= -1;
                x = Math.max(left, Math.min(right - w, x));
            }
        }

        void draw(GraphicsContext g) {
            g.setFill(Color.rgb(220, 55, 65));
            g.fillRect(x + 4, y + 5, 26, 20);
            g.fillRect(x + 8, y, 18, 25);

            g.setFill(Color.WHITE);
            g.fillRect(x + 9, y + 8, 5, 6);
            g.fillRect(x + 21, y + 8, 5, 6);

            g.setFill(Color.BLACK);
            g.fillRect(x + 11, y + 10, 3, 3);
            g.fillRect(x + 23, y + 10, 3, 3);
        }
    }

    // Static bridge so nested Player can read the current key state.
    static class MainHolder {
        static Set<KeyCode> keys;
    }

    @Override
    public void init() {
        MainHolder.keys = keys;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
