package com.mestryer.pixelsky.core.repository;
import com.mestryer.pixelsky.core.crud.InMemoryCrudRepository;
import com.mestryer.pixelsky.core.model.Player;
public class PlayerRepository extends InMemoryCrudRepository<Player,String>{ public PlayerRepository(){super(Player::getId);} }
