package pe.edu.upeu.sisventas.Repository;

import pe.edu.upeu.sisventas.model.Categoria;
import pe.edu.upeu.sisventas.model.UnidMedida;

public class UnidadMedidaRepository extends Abstract_JpaRepository<UnidMedida, Long>{
    private long sequence=1;
    @Override
    protected Long getId(UnidMedida entity) {
        return entity.getIdUnidad();
    }

    @Override
    protected void setId(UnidMedida entity, Categoria aLong) {

    }

    @Override
    protected void setId(UnidMedida entity, long id) {
        entity.setIdUnidad(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }
}
