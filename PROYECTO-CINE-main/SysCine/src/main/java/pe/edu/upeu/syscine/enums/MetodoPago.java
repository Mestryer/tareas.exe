package pe.edu.upeu.syscine.enums;

import lombok.Getter;

@Getter
public enum MetodoPago {
    EFECTIVO("Efectivo"),
    TARJETA("Tarjeta de credito/debito"),
    YAPE_PLIN("Yape / Plin"),
    TRANSFERENCIA("Transferencia bancaria");

    private final String descripcion;

    MetodoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
