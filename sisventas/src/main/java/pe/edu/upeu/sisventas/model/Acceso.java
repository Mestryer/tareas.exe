package pe.edu.upeu.sisventas.model;

import lombok.Getter;
import lombok.Setter;
import pe.edu.upeu.sisventas.ENUM.MENUS;
import pe.edu.upeu.sisventas.ENUM.TipTap;

import javax.swing.*;
@Getter
@Setter

public class Acceso {
    String idacces;
    String urlAccess;
    String menuITEMnombre;
    MENUS menuNombre;
    TipTap NombreTab;
}
