package pe.edu.upeu.sisventas.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Abstract_JpaRepository<T, ID> implements iCrudGenericoRepository<T, ID> {
    protected final List<T> data=new ArrayList<>();
    protected abstract ID getId(T entity);
    protected abstract void  setId(T entity, ID id);
    protected abstract ID generateId();
    @Override
    public T save(T entity) {
        if(getId(entity)==null){
         setId(entity, generateId());
        }
        data.add(entity);
        return null;
    }

    @Override
    public T update(T entity) {
        ID id=getId(entity);
        for (int i=0; i<data.size(); i++){
            T current =data.get(i);
            if (getId(current).equals(id)){
                data.set(i, entity);
                return entity;
            }
        }
        throw new RuntimeException(
                "no se encontro la emtidad con ID: " + id
        );
    }

    @Override
    public Optional<T> findById(ID id) {
        return data.stream()
                .filter(entity->getId(entity).equals(id))
                .findFirst();

    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public void deleteById(ID id) {
        data.removeIf(emtity->getId(emtity).equals(id));
    }

    @Override
    public boolean existsById(ID id) {
        return data.stream().anyMatch(emtity->getId(emtity).equals(id));
    }
}
