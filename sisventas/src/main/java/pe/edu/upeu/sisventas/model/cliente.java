package pe.edu.upeu.sisventas.model;

import lombok.Getter;
import lombok.Setter;
import pe.edu.upeu.sisventas.ENUM.TIPODOC;


@Getter
@Setter
public class cliente {
    String DNIRUC;
    String NOMBRES;
    String REPLEGAL;
    TIPODOC tipodocumento;

}
