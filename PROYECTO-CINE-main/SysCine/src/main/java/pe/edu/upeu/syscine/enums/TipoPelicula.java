package pe.edu.upeu.syscine.enums;

import lombok.Getter;

@Getter
public enum TipoPelicula {
    ESTRENO("Estreno"),
    CARTELERA("En cartelera"),
    PROXIMAMENTE("Proximamente"),
    CLASICA("Clasica");

    String descripcion;

    TipoPelicula(String descripcion){
        this.descripcion = descripcion;
    }
}
