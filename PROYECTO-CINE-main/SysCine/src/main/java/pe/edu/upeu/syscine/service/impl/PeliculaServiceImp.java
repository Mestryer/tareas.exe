package pe.edu.upeu.syscine.service.impl;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.enums.TipoPelicula;
import pe.edu.upeu.syscine.exception.ModelNotFoundException;
import pe.edu.upeu.syscine.model.Pelicula;
import pe.edu.upeu.syscine.repository.ICrudGenericoRepository;
import pe.edu.upeu.syscine.repository.PeliculaRepository;
import pe.edu.upeu.syscine.service.IPeliculaService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PeliculaServiceImp extends CrudGenericoServiceImp<Pelicula, Long> implements IPeliculaService {
    private final PeliculaRepository peliculaRepository;

    @Override
    protected ICrudGenericoRepository<Pelicula, Long> getRepo() {
        return peliculaRepository;
    }

    @Override
    public List<ComboBoxOption> listarTipoPelicula() {
        List<ComboBoxOption> listar = new ArrayList<>();
        for (TipoPelicula tp : TipoPelicula.values()) {
            listar.add(new ComboBoxOption(tp.name(), tp.getDescripcion()));
        }
        return listar;
    }

    @Override
    public List<Pelicula> findAll() {
        if (peliculaRepository.findAll().isEmpty()) {
            peliculaRepository.seedData();
        }
        return peliculaRepository.findAll();
    }

    @Override
    public List<Pelicula> buscarPorNombre(String texto) {
        String filtro = texto == null ? "" : texto.trim().toLowerCase();
        if (filtro.isEmpty()) {
            return findAll();
        }
        return findAll().stream()
                .filter(p -> p.getNombre() != null && p.getNombre().toLowerCase().contains(filtro))
                .toList();
    }

    @Override
    public Pelicula comprarBoletos(Long idPelicula, int cantidad) {
        Pelicula p = findById(idPelicula);
        if (p.getBoletosDisponibles() == null || p.getBoletosDisponibles() < cantidad) {
            throw new ModelNotFoundException("No hay boletos disponibles suficientes para esta funcion");
        }
        p.setBoletosDisponibles(p.getBoletosDisponibles() - cantidad);
        p.setBoletosVendidos((p.getBoletosVendidos() == null ? 0.0 : p.getBoletosVendidos()) + cantidad);
        return peliculaRepository.update(p);
    }
}
