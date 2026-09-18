package pe.edu.upeu.sisventas.Repository;

import pe.edu.upeu.sisventas.model.Categoria;
import pe.edu.upeu.sisventas.model.Marca;

public class MarcaRepository extends Abstract_JpaRepository<Marca,Long> {
    private long sequence=1;
    @Override
    protected Long getId(Marca entity) {
        return entity.getIdMarca();
    }

    @Override
    protected void setId(Marca entity, Categoria id) {
        entity.setIdMarca(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    public void seedData(){
        if (findAll().isEmpty()){
            save(new Marca(generateId(),"Samsung"));
            save(new Marca(generateId(), "LG"));
            save(new Marca(generateId(), "Apple"));
            save(new Marca(generateId(), "HP"));
            save(new Marca(generateId(),"Lenovo"));
        }
    }
}
