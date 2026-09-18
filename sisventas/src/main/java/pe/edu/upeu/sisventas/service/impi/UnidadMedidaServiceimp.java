package pe.edu.upeu.sisventas.service.impi;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sisventas.Repository.UnidadMedidaRepository;
import pe.edu.upeu.sisventas.model.UnidMedida;
import pe.edu.upeu.sisventas.service.IUnidadMedidaService;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

@RequiredArgsConstructor
public class UnidadMedidaServiceimp extends ICrudGenericoServiceImp<UnidMedida, Long> implements IUnidadMedidaService {
    private final UnidadMedidaRepository unidMedidaReposity;



    @Override
    protected IcrudGenericoService<UnidMedida, Long> getRepo() {

        return unidMedidaReposity;
    }

    @Override
    public UnidMedida findAll(Long aLong) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
