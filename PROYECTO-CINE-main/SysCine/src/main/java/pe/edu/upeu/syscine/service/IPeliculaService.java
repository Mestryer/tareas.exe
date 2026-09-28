package pe.edu.upeu.syscine.service;

import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.model.Pelicula;

import java.util.List;

public interface IPeliculaService extends ICrudGenericoService<Pelicula, Long>{
    List<ComboBoxOption> listarTipoPelicula();
    List<Pelicula> buscarPorNombre(String texto);
    Pelicula comprarBoletos(Long idPelicula, int cantidad);
}
