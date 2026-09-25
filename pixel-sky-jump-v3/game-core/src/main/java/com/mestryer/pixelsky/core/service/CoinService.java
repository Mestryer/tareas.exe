package com.mestryer.pixelsky.core.service;
import com.mestryer.pixelsky.core.model.Coin;
import com.mestryer.pixelsky.core.repository.CoinRepository;
import java.util.List;
public class CoinService {
 private final CoinRepository repository=new CoinRepository();
 public Coin create(Coin e){return repository.create(e);}
 public Coin read(String id){return repository.read(id).orElse(null);}
 public List<Coin> readAll(){return repository.readAll();}
 public Coin update(String id,Coin e){return repository.update(id,e);}
 public boolean delete(String id){return repository.delete(id);}
}
