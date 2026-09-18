package pe.edu.upeu.sisventas.service.impi;

import pe.edu.upeu.sisventas.Repository.MarcaRepository;
import pe.edu.upeu.sisventas.dto.ComboBoxOption;
import pe.edu.upeu.sisventas.model.Marca;
import pe.edu.upeu.sisventas.model.UnidMedida;
import pe.edu.upeu.sisventas.service.IMarcaService;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

import java.util.List;

public class MarcaServiceimp extends ICrudGenericoServiceImp<Marca, Long> implements IMarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaServiceimp(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }


    @Override
    protected IcrudGenericoService<UnidMedida, Long> getRepo() {
        return null;
    }

    @Override
    public List<ComboBoxOption> listarTipoPoducto() {
        return List.of();
    }

    @Override
    public Marca findAll(Long aLong) {
        return null;
    }
}
