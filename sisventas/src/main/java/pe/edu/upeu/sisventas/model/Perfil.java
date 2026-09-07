package pe.edu.upeu.sisventas.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Perfil {
    long idperfil;
    String nombre;
    String codigo;
    List<Acceso> accesos;
}