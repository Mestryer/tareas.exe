package pe.edu.upeu.syscine.repository;

import pe.edu.upeu.syscine.enums.TipoPelicula;
import pe.edu.upeu.syscine.model.Formato;
import pe.edu.upeu.syscine.model.Genero;
import pe.edu.upeu.syscine.model.Pelicula;
import pe.edu.upeu.syscine.model.Sala;

public class PeliculaRepository extends AbstractJpaRepository<Pelicula, Long>{
    private long sequence=1;
    @Override
    protected Long getId(Pelicula entity) {
        return entity.getIdPelicula();
    }

    @Override
    protected void setId(Pelicula entity, Long id) {
        entity.setIdPelicula(id);
    }

    @Override
    protected Long generateId() {
        return sequence++;
    }

    public void seedData() {
        if (findAll().isEmpty()) {

            Genero cienciaFiccion = new Genero(); cienciaFiccion.setIdGenero(1L);
            Genero comedia = new Genero(); comedia.setIdGenero(2L);
            Genero terror = new Genero(); terror.setIdGenero(3L);

            Sala salaVip = new Sala(); salaVip.setIdSala(3L);
            Sala sala1 = new Sala(); sala1.setIdSala(1L);
            Sala sala2 = new Sala(); sala2.setIdSala(2L);

            Formato f3d = new Formato(); f3d.setIdFormato(2L);
            Formato f2d = new Formato(); f2d.setIdFormato(1L);
            Formato f4dx = new Formato(); f4dx.setIdFormato(3L);

            save(new Pelicula(generateId(), "El Ultimo Horizonte", TipoPelicula.ESTRENO,
                    25.00, 20.00, 128.0, 80.0, 0.0, cienciaFiccion, salaVip, f3d));

            save(new Pelicula(generateId(), "Risas en Cadena", TipoPelicula.CARTELERA,
                    18.00, 18.00, 95.0, 120.0, 0.0, comedia, sala1, f2d));

            save(new Pelicula(generateId(), "Sombra Eterna", TipoPelicula.ESTRENO,
                    30.00, 30.00, 105.0, 60.0, 0.0, terror, sala2, f4dx));
        }
    }
}
