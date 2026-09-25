package com.mestryer.pixelsky.core.service;

import com.mestryer.pixelsky.core.model.Level;
import com.mestryer.pixelsky.core.repository.LevelRepository;
import com.mestryer.pixelsky.core.levels.Level1;
import com.mestryer.pixelsky.core.levels.Level2;
import com.mestryer.pixelsky.core.levels.Level3;

import java.util.Comparator;
import java.util.List;

public class LevelCrudService {
    private final LevelRepository repository = new LevelRepository();

    public Level create(Level level) { return repository.create(level); }
    public Level read(String id) { return repository.read(id).orElse(null); }
    public List<Level> readAll() {
        return repository.readAll().stream()
                .sorted(Comparator.comparingInt(Level::getNumber))
                .toList();
    }
    public Level update(String id, Level level) { return repository.update(id, level); }
    public boolean delete(String id) { return repository.delete(id); }

    public void loadDefaults() {
        if (!readAll().isEmpty()) return;
        create(new Level1().build());
        create(new Level2().build());
        create(new Level3().build());
    }
}
