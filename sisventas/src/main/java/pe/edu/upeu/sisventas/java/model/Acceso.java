package pe.edu.upeu.sisventas.java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.sisventas.ENUM.MENUS;
import pe.edu.upeu.sisventas.ENUM.TipTap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acceso {
    String idAcceso;
    String urlAcceso;
    MENUS menuNombre;
    String menuItemNombre;
    TipTap nombreTab;
}
