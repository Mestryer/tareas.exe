package pe.edu.upeu.syscine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genero {
    private Long idGenero;
    private String nombre;
}
