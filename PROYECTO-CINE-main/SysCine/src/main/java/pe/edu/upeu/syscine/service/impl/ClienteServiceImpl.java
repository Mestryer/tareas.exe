package pe.edu.upeu.syscine.service.impl;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.syscine.model.Cliente;
import pe.edu.upeu.syscine.repository.ClienteRepository;
import pe.edu.upeu.syscine.repository.ICrudGenericoRepository;
import pe.edu.upeu.syscine.service.IClienteService;

import java.util.List;

@RequiredArgsConstructor
public class ClienteServiceImpl extends CrudGenericoServiceImp<Cliente, Long> implements IClienteService {
    private final ClienteRepository clienteRepository;

    @Override
    protected ICrudGenericoRepository<Cliente, Long> getRepo() {
        return clienteRepository;
    }

    @Override
    public List<Cliente> findAll() {
        if (clienteRepository.findAll().isEmpty()) {
            clienteRepository.seedData();
        }
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> buscarPorTexto(String texto) {
        String filtro = texto == null ? "" : texto.trim().toLowerCase();
        if (filtro.isEmpty()) {
            return findAll();
        }
        return findAll().stream()
                .filter(c -> (c.getNombres() != null && c.getNombres().toLowerCase().contains(filtro))
                        || (c.getDniruc() != null && c.getDniruc().toLowerCase().contains(filtro)))
                .toList();
    }
}
