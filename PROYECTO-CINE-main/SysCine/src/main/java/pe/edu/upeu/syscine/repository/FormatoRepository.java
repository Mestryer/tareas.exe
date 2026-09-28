package pe.edu.upeu.syscine.repository;

import pe.edu.upeu.syscine.model.Formato;

public class FormatoRepository extends AbstractJpaRepository<Formato, Long>{
    private long sequence=1;
    @Override
    protected Long getId(Formato entity) {
        return entity.getIdFormato();
    }

    @Override
    protected void setId(Formato entity, Long id) {
        entity.setIdFormato(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    public void seedData() {
        if (findAll().isEmpty()) {
            save(new Formato(generateId(), "2D"));
            save(new Formato(generateId(), "3D"));
            save(new Formato(generateId(), "4DX"));
        }
    }
}
