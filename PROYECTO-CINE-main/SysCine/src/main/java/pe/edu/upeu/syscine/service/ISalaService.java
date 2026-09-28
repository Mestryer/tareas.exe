package pe.edu.upeu.syscine.service;

import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.model.Sala;

import java.util.List;

public interface ISalaService extends ICrudGenericoService<Sala, Long>{
    List<ComboBoxOption> listarCombobox();
}
