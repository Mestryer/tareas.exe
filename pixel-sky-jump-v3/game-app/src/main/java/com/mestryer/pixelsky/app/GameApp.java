package com.mestryer.pixelsky.app;

import com.mestryer.pixelsky.app.controller.GameController;
import com.mestryer.pixelsky.app.engine.GameEngine;
import com.mestryer.pixelsky.app.render.GameRenderer;
import com.mestryer.pixelsky.app.service.GameApplicationService;
import com.mestryer.pixelsky.app.ui.LevelCrudWindow;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameApp extends Application {
    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        GameApplicationService app = new GameApplicationService();
        app.initialize(stage);

        GameEngine engine = app.getEngine();
        GameController controller = new GameController(engine, app.getLevelService(), stage);
        GameRenderer renderer = new GameRenderer();

        controller.install(scene);

        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.F2) {
                new LevelCrudWindow(app.getLevelService()).show();
                event.consume();
            }
        });

        stage.setTitle("Pixel Sky Jump | Mestryer");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(960);
        stage.setMinHeight(600);
        stage.show();

        root.widthProperty().addListener((obs, oldValue, newValue) -> resizeCanvas(canvas, root));
        root.heightProperty().addListener((obs, oldValue, newValue) -> resizeCanvas(canvas, root));

        final long[] last = {System.nanoTime()};

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double dt = Math.min((now - last[0]) / 1_000_000_000.0, 0.033);
                last[0] = now;

                controller.update(dt, scene);
                renderFrame(canvas, renderer, engine);
            }
        }.start();
    }

    private void renderFrame(Canvas canvas, GameRenderer renderer, GameEngine engine) {
        double scale = Math.min(canvas.getWidth() / GameRenderer.VIEW_WIDTH,
                canvas.getHeight() / GameRenderer.VIEW_HEIGHT);
        double offsetX = (canvas.getWidth() - GameRenderer.VIEW_WIDTH * scale) / 2.0;
        double offsetY = (canvas.getHeight() - GameRenderer.VIEW_HEIGHT * scale) / 2.0;

        var graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphics.save();
        graphics.translate(offsetX, offsetY);
        graphics.scale(scale, scale);
        renderer.render(graphics, engine);
        graphics.restore();
    }

    private void resizeCanvas(Canvas canvas, StackPane root) {
        canvas.setWidth(Math.max(GameRenderer.VIEW_WIDTH, root.getWidth()));
        canvas.setHeight(Math.max(GameRenderer.VIEW_HEIGHT, root.getHeight()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
