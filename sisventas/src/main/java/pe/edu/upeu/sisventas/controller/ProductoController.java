package pe.edu.upeu.sisventas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sisventas.dto.ComboBoxOption;
import pe.edu.upeu.sisventas.service.ICategoriaService;
import pe.edu.upeu.sisventas.service.IMarcaService;
import pe.edu.upeu.sisventas.service.IProductoService;
import pe.edu.upeu.sisventas.service.IUnidadMedidaService;

@RequiredArgsConstructor
public class ProductoController {
    private final IMarcaService ms;
    private final ICategoriaService cs;
    private final IUnidadMedidaService ums;
    private final IProductoService ps;

    @FXML ComboBox<ComboBoxOption> cbxTIPPRODUC;

    @FXML
    public void initialize(){
        System.out.println("holass");

        cbxTIPPRODUC.getItems().addAll(ps.listarTipoPoducto());

    }
}
