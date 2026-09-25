package com.mestryer.pixelsky.core.repository;
import com.mestryer.pixelsky.core.crud.InMemoryCrudRepository;
import com.mestryer.pixelsky.core.model.Enemy;
public class EnemyRepository extends InMemoryCrudRepository<Enemy,String>{ public EnemyRepository(){super(Enemy::getId);} }
