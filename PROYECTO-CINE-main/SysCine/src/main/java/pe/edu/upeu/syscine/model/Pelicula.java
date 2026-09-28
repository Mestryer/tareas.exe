package pe.edu.upeu.syscine.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.syscine.enums.TipoPelicula;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    private Long idPelicula;
    @NotBlank(message = "El nombre de la pelicula es obligatorio")
    private String nombre;
    @NotNull(message = "El tipo de pelicula es obligatorio")
    private TipoPelicula tipoPelicula;
    @NotNull(message = "El precio de la entrada es obligatorio")
    @Positive(message = "El precio de la entrada debe ser positivo")
    private Double precioEntrada;
    @NotNull(message = "El precio anterior de la entrada es obligatorio")
    @PositiveOrZero(message = "El precio anterior debe ser positivo o cero")
    private Double precioEntradaOld;
    @NotNull(message = "La duracion es obligatoria")
    @PositiveOrZero(message = "La duracion debe ser positiva o cero")
    private Double duracionMin;
    @NotNull(message = "Los boletos disponibles son obligatorios")
    @PositiveOrZero(message = "Los boletos disponibles deben ser positivos")
    private Double boletosDisponibles;
    @NotNull(message = "Los boletos vendidos son obligatorios")
    @PositiveOrZero(message = "Los boletos vendidos deben ser positivos")
    private Double boletosVendidos;
    @NotNull(message = "El genero de la pelicula es obligatorio")
    private Genero idGenero;
    @NotNull(message = "La sala de la pelicula es obligatoria")
    private Sala idSala;
    @NotNull(message = "El formato de la pelicula es obligatorio")
    private Formato idFormato;
}
