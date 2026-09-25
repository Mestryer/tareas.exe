package com.mestryer.pixelsky.core.repository;
import com.mestryer.pixelsky.core.crud.InMemoryCrudRepository;
import com.mestryer.pixelsky.core.model.Level;
public class LevelRepository extends InMemoryCrudRepository<Level,String>{ public LevelRepository(){super(Level::getId);} }
