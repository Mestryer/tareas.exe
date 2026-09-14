package pe.edu.upeu.sisventas.Repository;

import pe.edu.upeu.sisventas.model.UnidMedida;

public class UnidadMedidaRepository extends Abstract_JpaRepository<UnidMedida, long>{
    private long sequence=1;
    @Override
    protected long getId(UnidMedida entity) {
        return entity.getIdUnidad();
    }

    @Override
    protected void setId(UnidMedida entity, long id) {
        entity.setIdUnidad(id);
    }

    @Override
    protected long generateId() {
        return sequence++;
    }
}
