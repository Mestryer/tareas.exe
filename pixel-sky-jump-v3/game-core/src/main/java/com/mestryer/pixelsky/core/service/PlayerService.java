package com.mestryer.pixelsky.core.service;
import com.mestryer.pixelsky.core.model.Player;
import com.mestryer.pixelsky.core.repository.PlayerRepository;
import java.util.List;
public class PlayerService {
 private final PlayerRepository repository=new PlayerRepository();
 public Player create(Player e){return repository.create(e);}
 public Player read(String id){return repository.read(id).orElse(null);}
 public List<Player> readAll(){return repository.readAll();}
 public Player update(String id,Player e){return repository.update(id,e);}
 public boolean delete(String id){return repository.delete(id);}
}
