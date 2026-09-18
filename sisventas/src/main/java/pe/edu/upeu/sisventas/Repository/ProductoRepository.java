package pe.edu.upeu.sisventas.Repository;

import pe.edu.upeu.sisventas.model.Categoria;
import pe.edu.upeu.sisventas.model.Producto;
import pe.edu.upeu.sisventas.model.UnidMedida;

public class ProductoRepository extends Abstract_JpaRepository<Producto, Long> {
    private long sequence = 1;
    @Override
    protected Long getId(Producto entity) {
        return entity.getIdProducto();
    }

    @Override
    protected void setId(Producto entity, Categoria id) {
        entity.setIdCategoria(id);
    }

    @Override
    protected void setId(UnidMedida entity, long id) {

    }

    @Override
    protected Long generateId() {
        return sequence++;
    }
}
