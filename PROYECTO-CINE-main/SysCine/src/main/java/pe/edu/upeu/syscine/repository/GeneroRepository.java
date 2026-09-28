package pe.edu.upeu.syscine.repository;

import pe.edu.upeu.syscine.model.Genero;

public class GeneroRepository extends AbstractJpaRepository<Genero, Long>{
    private long sequence=1;
    @Override
    protected Long getId(Genero entity) {
        return entity.getIdGenero();
    }

    @Override
    protected void setId(Genero entity, Long id) {
        entity.setIdGenero(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    public void seedData() {
        if (findAll().isEmpty()) {
            save(new Genero(generateId(), "Ciencia Ficcion"));
            save(new Genero(generateId(), "Comedia"));
            save(new Genero(generateId(), "Terror"));
            save(new Genero(generateId(), "Drama"));
            save(new Genero(generateId(), "Accion"));
        }
    }
}
