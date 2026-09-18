package pe.edu.upeu.sisventas.service.impi;

import pe.edu.upeu.sisventas.ENUM.TIPPRODUC;
import pe.edu.upeu.sisventas.Repository.CategoriaRepository;
import pe.edu.upeu.sisventas.dto.ComboBoxOption;
import pe.edu.upeu.sisventas.model.Categoria;
import pe.edu.upeu.sisventas.model.UnidMedida;
import pe.edu.upeu.sisventas.service.ICategoriaService;
import pe.edu.upeu.sisventas.service.IcrudGenericoService;

import java.util.ArrayList;
import java.util.List;

public class CategoriaServiceimp extends ICrudGenericoServiceImp<Categoria, Long> implements ICategoriaService {
    private final CategoriaRepository categoriaepository;

    public CategoriaServiceimp(CategoriaRepository categoriarepository) {
        this.categoriaepository = categoriarepository;
    }

    @Override
    protected IcrudGenericoService<UnidMedida, Long> getRepo() {
        return (IcrudGenericoService<UnidMedida, Long>) categoriaepository;
    }

    @Override
    public List<ComboBoxOption> listarTipoPoducto() {
        List<ComboBoxOption> listar = new ArrayList<>();
        for (TIPPRODUC tp : TIPPRODUC.values()) {
            ComboBoxOption cb = new ComboBoxOption();
            cb.setKey(tp.getDescripcioN());
            listar.add(cb);
        }
        return listar;
    }

    @Override
    public Categoria findAll(Long aLong) {
        return null;
    }
}
