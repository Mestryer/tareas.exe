package pe.edu.upeu.syscine.repository;

import pe.edu.upeu.syscine.enums.TipoDocumento;
import pe.edu.upeu.syscine.model.Cliente;

public class ClienteRepository extends AbstractJpaRepository<Cliente, Long>{
    private long sequence=1;
    @Override
    protected Long getId(Cliente entity) {
        return entity.getIdCliente();
    }

    @Override
    protected void setId(Cliente entity, Long id) {
        entity.setIdCliente(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    public void seedData() {
        if (findAll().isEmpty()) {
            save(new Cliente(generateId(), "20456789123", "Cine Fans Peru S.A.C.", "Ana Torres", TipoDocumento.RUC));
            save(new Cliente(generateId(), "70123456", "Maria Lopez Quispe", "", TipoDocumento.DNI));
        }
    }
}
