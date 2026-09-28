package pe.edu.upeu.syscine.service;

import pe.edu.upeu.syscine.model.Cliente;

import java.util.List;

public interface IClienteService extends ICrudGenericoService<Cliente, Long>{
    List<Cliente> buscarPorTexto(String texto);
}
