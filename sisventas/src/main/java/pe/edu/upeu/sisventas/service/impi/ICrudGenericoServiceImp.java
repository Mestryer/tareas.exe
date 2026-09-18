package pe.edu.upeu.sisventas.service.impi;

import pe.edu.upeu.sisventas.dto.ComboBoxOption;
import pe.edu.upeu.sisventas.exception.ModelNotFoundException;
import pe.edu.upeu.sisventas.model.UnidMedida;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

import java.util.List;
import java.util.Optional;

public abstract class ICrudGenericoServiceImp<T, ID> implements IcrudGenericoService<T, ID> {
    protected abstract IcrudGenericoService<UnidMedida, Long> getRepo();

    @Override
    public T save(T t) {
        return (T) getRepo().save(T, t);
    }

    @Override
    public T update(ID id, T t) {
        if(!getRepo().existsById(ID, id)){
            throw new ModelNotFoundException("ID no existe: "+id);
        }
        return getRepo().update(T, t);
    }

    @Override
    public List<T> findAll() {
        return (List<T>) getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id)
                .orElseThrow(()->new ModelNotFoundException("El Id no existe:"+id));
    }

    @Override
    public void delete(ID id) {
        if(!getRepo().existsById((Long) id)){
            throw new ModelNotFoundException("ID no existe: "+id);
        }
        getRepo().deleteById((Long) id);
    }
    public interface ICrudGenericoRepository<T, ID> {
        T save(T entity);
        T update(T entity);
        Optional<T> findById(ID id);
        List<T> findAll();
        void deleteById(ID id);
        boolean existsById(ID id);
    }
}
