package pe.edu.upeu.syscine.service;

import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.model.Formato;

import java.util.List;

public interface IFormatoService extends ICrudGenericoService<Formato, Long>{
    List<ComboBoxOption> listarCombobox();
}
