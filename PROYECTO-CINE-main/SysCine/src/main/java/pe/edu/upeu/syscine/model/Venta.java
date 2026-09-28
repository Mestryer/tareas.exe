package pe.edu.upeu.syscine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.syscine.enums.MetodoPago;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    private Long idVenta;
    private Long idPelicula;
    private String nombrePelicula;
    private String dniCliente;
    private MetodoPago metodoPago;
    private Integer cantidadBoletos;
    private Double total;
}
