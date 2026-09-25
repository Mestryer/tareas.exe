package com.mestryer.pixelsky.core.repository;
import com.mestryer.pixelsky.core.crud.InMemoryCrudRepository;
import com.mestryer.pixelsky.core.model.FloatingIsland;
public class FloatingIslandRepository extends InMemoryCrudRepository<FloatingIsland,String>{ public FloatingIslandRepository(){super(FloatingIsland::getId);} }
