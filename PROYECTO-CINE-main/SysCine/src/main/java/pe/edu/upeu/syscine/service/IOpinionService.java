package pe.edu.upeu.syscine.service;

import pe.edu.upeu.syscine.model.Opinion;

import java.util.List;

public interface IOpinionService extends ICrudGenericoService<Opinion, Long>{
    List<Opinion> listarPorPelicula(Long idPelicula);
    double promedioPuntuacion(Long idPelicula);
}
