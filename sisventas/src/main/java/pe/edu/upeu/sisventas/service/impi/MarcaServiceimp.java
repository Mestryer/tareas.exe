package pe.edu.upeu.sisventas.service.impi;

import pe.edu.upeu.sisventas.Repository.MarcaRepository;
import pe.edu.upeu.sisventas.model.Marca;
import pe.edu.upeu.sisventas.service.IMarcaService;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

public class MarcaServiceimp extends CrudGenericoServiceimp<Marca, Long> implements IMarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaServiceimp(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    protected IcrudGenericoService<Marca, Long> getRepo() {
        return marcaRepository;
    }
}
