package pe.edu.upeu.syscine.service;

import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.model.Genero;

import java.util.List;

public interface IGeneroService extends ICrudGenericoService<Genero, Long>{
    List<ComboBoxOption> listarCombobox();
}
