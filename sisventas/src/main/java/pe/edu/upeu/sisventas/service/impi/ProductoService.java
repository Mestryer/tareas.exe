package pe.edu.upeu.sisventas.service.impi;

import pe.edu.upeu.sisventas.ENUM.TIPPRODUC;
import pe.edu.upeu.sisventas.dto.ComboBoxOption;
import pe.edu.upeu.sisventas.model.Producto;
import pe.edu.upeu.sisventas.model.UnidMedida;
import pe.edu.upeu.sisventas.service.IProductoService;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

import java.util.ArrayList;
import java.util.List;

public class ProductoService extends ICrudGenericoServiceImp<Producto, Long> implements IProductoService {
    private final Producto productoRepository;

    public ProductoService(Producto productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    protected IcrudGenericoService<UnidMedida, Long> getRepo() {
        return (IcrudGenericoService<UnidMedida, Long>) productoRepository;
    }

    @Override
    public List<ComboBoxOption> listarTipoPoducto() {
        List<ComboBoxOption> listar = new ArrayList<>();
        for (TIPPRODUC tp: TIPPRODUC.values()){
            ComboBoxOption cb=new ComboBoxOption();
            cb.setKey(tp.getDescripcioN());
            listar.add(cb);
        }
        return listar;
    }
}
