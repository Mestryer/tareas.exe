package pe.edu.upeu.syscine.service.impl;

import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.model.Formato;
import pe.edu.upeu.syscine.repository.FormatoRepository;
import pe.edu.upeu.syscine.repository.ICrudGenericoRepository;
import pe.edu.upeu.syscine.service.IFormatoService;

import java.util.ArrayList;
import java.util.List;

public class FormatoServiceImp extends CrudGenericoServiceImp<Formato, Long> implements IFormatoService {

    private final FormatoRepository formatoRepository;

    public FormatoServiceImp(FormatoRepository formatoRepository) {
        this.formatoRepository = formatoRepository;
    }

    @Override
    protected ICrudGenericoRepository<Formato, Long> getRepo() {
        return formatoRepository;
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        if (formatoRepository.findAll().isEmpty()) {
            formatoRepository.seedData();
        }
        List<ComboBoxOption> listar = new ArrayList<>();
        for (Formato f : formatoRepository.findAll()) {
            listar.add(new ComboBoxOption(String.valueOf(f.getIdFormato()), f.getNombreFormato()));
        }
        return listar;
    }
}
