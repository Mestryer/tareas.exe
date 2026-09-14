package pe.edu.upeu.sisventas.service;

import pe.edu.upeu.sisventas.dto.ComboBoxOption;
import pe.edu.upeu.sisventas.model.Producto;

import java.util.List;

public interface IProductoService extends IcrudGenericoService<Producto, Long>{
    List<ComboBoxOption> listarTipoPoducto();
}
