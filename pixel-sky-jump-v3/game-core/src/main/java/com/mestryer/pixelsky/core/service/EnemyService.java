package com.mestryer.pixelsky.core.service;
import com.mestryer.pixelsky.core.model.Enemy;
import com.mestryer.pixelsky.core.repository.EnemyRepository;
import java.util.List;
public class EnemyService {
 private final EnemyRepository repository=new EnemyRepository();
 public Enemy create(Enemy e){return repository.create(e);}
 public Enemy read(String id){return repository.read(id).orElse(null);}
 public List<Enemy> readAll(){return repository.readAll();}
 public Enemy update(String id,Enemy e){return repository.update(id,e);}
 public boolean delete(String id){return repository.delete(id);}
}
