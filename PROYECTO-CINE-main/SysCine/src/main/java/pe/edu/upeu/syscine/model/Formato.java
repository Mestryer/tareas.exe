package pe.edu.upeu.syscine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Formato {
    private Long idFormato;
    private String nombreFormato;
}
