package pe.edu.upeu.sisventas.Repository;

import pe.edu.upeu.sisventas.model.Marca;

public class MarcaRepository extends Abstract_JpaRepository<Marca,Long> {
    private long sequence=1;
    @Override
    protected Long getId(Marca entity) {
        return entity.getIdMarca();
    }

    @Override
    protected void setId(Marca entity, Long id) {
        entity.setIdMarca(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }
}
