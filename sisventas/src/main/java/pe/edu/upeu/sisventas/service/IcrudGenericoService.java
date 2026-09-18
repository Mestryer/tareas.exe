package pe.edu.upeu.sisventas.service;

import java.util.List;

public interface IcrudGenericoService <T, ID>{
    T save (T t);
    T update (ID id, T t);
    List<T> findAll();
    T findAll(ID id );

    T findById(ID id);

    void delete (ID id );


    boolean existsById(ID id);

    void deleteById(ID id);
}
