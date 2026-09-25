package com.mestryer.pixelsky.core.repository;
import com.mestryer.pixelsky.core.crud.InMemoryCrudRepository;
import com.mestryer.pixelsky.core.model.Coin;
public class CoinRepository extends InMemoryCrudRepository<Coin,String>{ public CoinRepository(){super(Coin::getId);} }
