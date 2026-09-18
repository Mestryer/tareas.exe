package pe.edu.upeu.sisventas.Repository;

import pe.edu.upeu.sisventas.model.Categoria;

public class CategoriaRepository extends Abstract_JpaRepository<Categoria, Long> {
    private long sequence=1;

    @Override
    protected Long getId(Categoria entity) {
        return entity.getIdCategoria();
    }

    @Override
    protected void setId(Categoria entity, Categoria id) {
        entity.setIdCategoria(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }
}
