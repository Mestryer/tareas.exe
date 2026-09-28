package pe.edu.upeu.syscine.service.impl;

import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.model.Genero;
import pe.edu.upeu.syscine.repository.GeneroRepository;
import pe.edu.upeu.syscine.repository.ICrudGenericoRepository;
import pe.edu.upeu.syscine.service.IGeneroService;

import java.util.ArrayList;
import java.util.List;

public class GeneroServiceImp extends CrudGenericoServiceImp<Genero, Long> implements IGeneroService {

    private final GeneroRepository generoRepository;

    public GeneroServiceImp(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    protected ICrudGenericoRepository<Genero, Long> getRepo() {
        return generoRepository;
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        if (generoRepository.findAll().isEmpty()) {
            generoRepository.seedData();
        }
        List<ComboBoxOption> listar = new ArrayList<>();
        for (Genero g : generoRepository.findAll()) {
            listar.add(new ComboBoxOption(String.valueOf(g.getIdGenero()), g.getNombre()));
        }
        return listar;
    }
}
