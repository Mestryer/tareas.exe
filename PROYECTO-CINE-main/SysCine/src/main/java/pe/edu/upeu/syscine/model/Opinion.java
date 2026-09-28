package pe.edu.upeu.syscine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Opinion {
    private Long idOpinion;
    private Long idPelicula;
    private String usuario;
    private String comentario;
    private Integer puntuacion; // 1 a 5
}
