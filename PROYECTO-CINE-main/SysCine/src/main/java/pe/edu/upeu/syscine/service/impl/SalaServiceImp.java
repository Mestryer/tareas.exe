package pe.edu.upeu.syscine.service.impl;

import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.model.Sala;
import pe.edu.upeu.syscine.repository.ICrudGenericoRepository;
import pe.edu.upeu.syscine.repository.SalaRepository;
import pe.edu.upeu.syscine.service.ISalaService;

import java.util.ArrayList;
import java.util.List;

public class SalaServiceImp extends CrudGenericoServiceImp<Sala, Long> implements ISalaService {

    private final SalaRepository salaRepository;

    public SalaServiceImp(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    protected ICrudGenericoRepository<Sala, Long> getRepo() {
        return salaRepository;
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        if (salaRepository.findAll().isEmpty()) {
            salaRepository.seedData();
        }
        List<ComboBoxOption> listar = new ArrayList<>();
        for (Sala s : salaRepository.findAll()) {
            listar.add(new ComboBoxOption(String.valueOf(s.getIdSala()), s.getNombre()));
        }
        return listar;
    }
}
