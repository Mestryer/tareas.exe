package pe.edu.upeu.sisventas.java.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.sisventas.ENUM.TIPODOC;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String dniruc;
    private String nombres;
    private TIPODOC tipoDocumento;
    private String repLegal;
    private String direccion;
}
