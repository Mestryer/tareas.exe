package pe.edu.upeu.sisventas.service.impi;

import pe.edu.upeu.sisventas.model.UnidMedida;
import pe.edu.upeu.sisventas.service.IUnidadMedidaService;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

public class UnidadMedidaServiceimp extends ICrudGenericoServiceImp<UnidMedida, Long> implements IUnidadMedidaService {
    private final UnidMedida unidMedida;

    public UnidadMedidaServiceimp(UnidMedida unidMedida) {
        this.unidMedida = unidMedida;
    }

    @Override
    protected IcrudGenericoService<UnidMedida, Long> getRepo() {
        return unidMedida;
    }
}
