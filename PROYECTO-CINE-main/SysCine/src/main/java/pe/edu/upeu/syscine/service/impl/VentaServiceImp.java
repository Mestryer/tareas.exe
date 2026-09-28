package pe.edu.upeu.syscine.service.impl;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.syscine.model.Venta;
import pe.edu.upeu.syscine.repository.ICrudGenericoRepository;
import pe.edu.upeu.syscine.repository.VentaRepository;
import pe.edu.upeu.syscine.service.IVentaService;

@RequiredArgsConstructor
public class VentaServiceImp extends CrudGenericoServiceImp<Venta, Long> implements IVentaService {
    private final VentaRepository ventaRepository;

    @Override
    protected ICrudGenericoRepository<Venta, Long> getRepo() {
        return ventaRepository;
    }
}
