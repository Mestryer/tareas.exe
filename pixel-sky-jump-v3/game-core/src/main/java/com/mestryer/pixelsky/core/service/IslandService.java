package com.mestryer.pixelsky.core.service;
import com.mestryer.pixelsky.core.model.FloatingIsland;
import com.mestryer.pixelsky.core.repository.IslandRepository;
import java.util.List;
public class IslandService {
 private final IslandRepository repository=new IslandRepository();
 public FloatingIsland create(FloatingIsland e){return repository.create(e);}
 public FloatingIsland read(String id){return repository.read(id).orElse(null);}
 public List<FloatingIsland> readAll(){return repository.readAll();}
 public FloatingIsland update(String id,FloatingIsland e){return repository.update(id,e);}
 public boolean delete(String id){return repository.delete(id);}
}
