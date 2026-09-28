package pe.edu.upeu.syscine.service.impl;

import pe.edu.upeu.syscine.model.Opinion;
import pe.edu.upeu.syscine.repository.ICrudGenericoRepository;
import pe.edu.upeu.syscine.repository.OpinionRepository;
import pe.edu.upeu.syscine.service.IOpinionService;

import java.util.List;

public class OpinionServiceImp extends CrudGenericoServiceImp<Opinion, Long> implements IOpinionService {

    private final OpinionRepository opinionRepository;

    public OpinionServiceImp(OpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
    }

    @Override
    protected ICrudGenericoRepository<Opinion, Long> getRepo() {
        return opinionRepository;
    }

    @Override
    public List<Opinion> listarPorPelicula(Long idPelicula) {
        if (opinionRepository.findAll().isEmpty()) {
            opinionRepository.seedData();
        }
        return opinionRepository.findByPelicula(idPelicula);
    }

    @Override
    public double promedioPuntuacion(Long idPelicula) {
        List<Opinion> opiniones = listarPorPelicula(idPelicula);
        if (opiniones.isEmpty()) return 0.0;
        return opiniones.stream().mapToInt(Opinion::getPuntuacion).average().orElse(0.0);
    }
}
