package pe.edu.upeu.sisventas.service.impi;

import pe.edu.upeu.sisventas.exception.ModelNotFoundException;
import pe.edu.upeu.sisventas.model.UnidMedida;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

import java.util.List;

public abstract class ICrudGenericoServiceImp<T, ID> implements IcrudGenericoService<T, ID> {
    protected abstract IcrudGenericoService<UnidMedida, Long> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(ID id, T t) {
        if(!getRepo().existsById(id)){
            throw new ModelNotFoundException("ID no existe: "+id);
        }
        return getRepo().update(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id).orElseThrow(()->new ModelNotFoundException("El Id no existe:"+id));
    }

    @Override
    public void delete(ID id) {
        if(!getRepo().existsById(id)){
            throw new ModelNotFoundException("ID no existe: "+id);
        }
        getRepo().deleteById(id);
    }
}
