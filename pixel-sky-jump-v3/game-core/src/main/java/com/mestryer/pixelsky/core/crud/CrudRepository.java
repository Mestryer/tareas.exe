package com.mestryer.pixelsky.core.crud;

import java.util.*;

public interface CrudRepository<T,ID>{
    T create(T e);
    Optional<T> read(ID id);
    List<T> readAll();
    T update(ID id,T e);
    boolean delete(ID id);
}