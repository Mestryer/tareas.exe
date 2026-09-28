package pe.edu.upeu.syscine.repository;

import pe.edu.upeu.syscine.model.Opinion;

import java.util.List;

public class OpinionRepository extends AbstractJpaRepository<Opinion, Long>{
    private long sequence=1;
    @Override
    protected Long getId(Opinion entity) {
        return entity.getIdOpinion();
    }

    @Override
    protected void setId(Opinion entity, Long id) {
        entity.setIdOpinion(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    public void seedData() {
        if (findAll().isEmpty()) {
            save(new Opinion(generateId(), 1L, "Carlos", "Excelentes efectos visuales, muy entretenida.", 5));
            save(new Opinion(generateId(), 2L, "Maria", "Me rei bastante, buena para ir en familia.", 4));
            save(new Opinion(generateId(), 3L, "Jorge", "Tensa de principio a fin.", 4));
        }
    }

    public List<Opinion> findByPelicula(Long idPelicula) {
        return findAll().stream().filter(o -> o.getIdPelicula().equals(idPelicula)).toList();
    }
}
