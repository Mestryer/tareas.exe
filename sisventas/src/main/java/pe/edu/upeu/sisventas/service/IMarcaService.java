package pe.edu.upeu.sisventas.service;

import pe.edu.upeu.sisventas.model.Marca;

import java.util.List;

public interface IMarcaService extends IcrudGenericoService<Marca, Long>{
    @Override
    List<Marca> findAll();
}
