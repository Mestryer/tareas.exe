package com.mestryer.pixelsky.core.repository;
import lombok.Getter;
@Getter
public class RepositoryRegistry {
 private final PlayerRepository players=new PlayerRepository();
 private final EnemyRepository enemies=new EnemyRepository();
 private final CoinRepository coins=new CoinRepository();
 private final FloatingIslandRepository islands=new FloatingIslandRepository();
 private final LevelRepository levels=new LevelRepository();
}
