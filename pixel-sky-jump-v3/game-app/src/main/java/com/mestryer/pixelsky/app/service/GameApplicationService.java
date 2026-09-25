package com.mestryer.pixelsky.app.service;

import com.mestryer.pixelsky.app.engine.GameEngine;
import com.mestryer.pixelsky.core.service.LevelCrudService;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class GameApplicationService {
    private final LevelCrudService levelService = new LevelCrudService();
    private final GameEngine engine = new GameEngine();
    private Stage stage;

    public void initialize(Stage stage) {
        this.stage = stage;
        levelService.loadDefaults();
    }
}
