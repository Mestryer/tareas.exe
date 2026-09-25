package com.mestryer.pixelsky.core.repository;

import com.mestryer.pixelsky.core.crud.InMemoryCrudRepository;
import com.mestryer.pixelsky.core.model.FloatingIsland;

public class IslandRepository extends InMemoryCrudRepository<FloatingIsland, String> {
    public IslandRepository() {
        super(FloatingIsland::getId);
    }
}
