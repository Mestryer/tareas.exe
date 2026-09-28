package pe.edu.upeu.syscine.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import pe.edu.upeu.syscine.components.ColumnInfo;
import pe.edu.upeu.syscine.components.TableViewHelper;
import pe.edu.upeu.syscine.components.Toast;
import pe.edu.upeu.syscine.components.ToltipCustom;
import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.enums.TipoDocumento;
import pe.edu.upeu.syscine.model.Cliente;
import pe.edu.upeu.syscine.service.IClienteService;
import java.util.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService clienteService;
    @FXML private ComboBox<ComboBoxOption> cbxTipoDocumento;
    @FXML private TextField txtDniruc, txtNombres, txtRepLegal, txtFiltroDato;

    @FXML private TableView<Cliente> tableView;
    private ObservableList<Cliente> listarClientes;

    private Cliente formulario;
    private Long idClienteCE = 0L;

    @FXML private Label lbnMsg;
    @FXML private AnchorPane miContenedor;
    private Stage stage;
    private Validator validator;
    private final ToltipCustom ttc = new ToltipCustom();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) miContenedor.getScene().getWindow();
        });

        if (cbxTipoDocumento != null) {
            for (TipoDocumento td : TipoDocumento.values()) {
                ComboBoxOption cb = new ComboBoxOption();
                cb.setKey(td.name());
                cb.setValue(td.name());
                cbxTipoDocumento.getItems().add(cb);
            }
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        TableViewHelper<Cliente> tableViewHelper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columns = new LinkedHashMap<>();
        columns.put("ID", new ColumnInfo("idCliente", 60.0));
        columns.put("DNI / RUC", new ColumnInfo("dniruc", 120.0));
        columns.put("Nombres / Razon Social", new ColumnInfo("nombres", 200.0));
        columns.put("Rep. Legal", new ColumnInfo("repLegal", 150.0));
        columns.put("Tipo Doc.", new ColumnInfo("tipoDocumento", 120.0));

        Consumer<Cliente> updateAction = c -> {
            editForm(c);
            idClienteCE = c.getIdCliente();
        };

        Consumer<Cliente> deleteAction = c -> {
            clienteService.delete(c.getIdCliente());
            Stage stage = (Stage) miContenedor.getScene().getWindow();
            double w = stage.getWidth() / 1.5, h = stage.getHeight() / 2;
            Toast.showToast(stage, "Se elimino correctamente!!", 2000, w, h);
            listar();
        };

        tableViewHelper.addColumnsInOrderWithSize(tableView, columns, updateAction, deleteAction);
        tableView.setTableMenuButtonVisible(true);

        if (txtFiltroDato != null) {
            txtFiltroDato.textProperty().addListener((obs, oldV, newV) -> buscarCliente());
        }

        listar();
    }

    @FXML
    public void buscarCliente() {
        String texto = txtFiltroDato == null ? "" : txtFiltroDato.getText();
        tableView.getItems().clear();
        tableView.getItems().addAll(clienteService.buscarPorTexto(texto));
    }

    public void listar() {
        try {
            tableView.getItems().clear();
            listarClientes = FXCollections.observableArrayList(clienteService.findAll());
            tableView.getItems().addAll(listarClientes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void validarFormulario() {
        formulario = new Cliente();
        formulario.setDniruc(txtDniruc.getText());
        formulario.setNombres(txtNombres.getText());
        formulario.setRepLegal(txtRepLegal.getText());

        String idxTD = cbxTipoDocumento.getSelectionModel().getSelectedItem() == null ? ""
                : cbxTipoDocumento.getSelectionModel().getSelectedItem().getKey();
        formulario.setTipoDocumento(idxTD.equals("") ? null : TipoDocumento.valueOf(idxTD));

        Set<ConstraintViolation<Cliente>> violaciones = validator.validate(formulario);
        List<ConstraintViolation<Cliente>> violacionesOrdenadas = violaciones.stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                .toList();

        if (violacionesOrdenadas.isEmpty()) {
            procesarFormulario();
        } else {
            mostrarErroresValidacion(violacionesOrdenadas);
        }
    }

    private void mostrarErroresValidacion(List<ConstraintViolation<Cliente>> violaciones) {
        limpiarError();
        Map<String, Control> campos = new LinkedHashMap<>();
        campos.put("dniruc", txtDniruc);
        campos.put("nombres", txtNombres);
        campos.put("repLegal", txtRepLegal);
        campos.put("tipoDocumento", cbxTipoDocumento);

        LinkedHashMap<String, String> erroresOrdenados = new LinkedHashMap<>();
        final Control[] primerCtrl = {null};
        for (String campo : campos.keySet()) {
            violaciones.stream()
                    .filter(v -> v.getPropertyPath().toString().equals(campo))
                    .findFirst().ifPresent(v -> {
                        erroresOrdenados.put(campo, v.getMessage());
                        Control c = campos.get(campo);
                        if (c != null) ttc.marcarError(c, v.getMessage().trim());
                        if (primerCtrl[0] == null) primerCtrl[0] = c;
                    });
        }

        if (!erroresOrdenados.isEmpty()) {
            lbnMsg.setText(erroresOrdenados.entrySet().iterator().next().getValue());
            lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            if (primerCtrl[0] != null) Platform.runLater(primerCtrl[0]::requestFocus);
        }
    }

    private void procesarFormulario() {
        lbnMsg.setText("Formulario valido");
        lbnMsg.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        Stage stage = (Stage) miContenedor.getScene().getWindow();
        limpiarError();
        double w = stage.getWidth() / 1.5, h = stage.getHeight() / 2;

        if (idClienteCE > 0L) {
            formulario.setIdCliente(idClienteCE);
            clienteService.update(idClienteCE, formulario);
            Toast.showToast(stage, "Se actualizo correctamente!!", 2000, w, h);
        } else {
            clienteService.save(formulario);
            Toast.showToast(stage, "Se guardo correctamente!!", 2000, w, h);
        }
        clearForm();
        listar();
    }

    public void editForm(Cliente cliente) {
        txtDniruc.setText(cliente.getDniruc());
        txtNombres.setText(cliente.getNombres());
        txtRepLegal.setText(cliente.getRepLegal());

        if (cliente.getTipoDocumento() != null) {
            cbxTipoDocumento.getSelectionModel().select(
                    cbxTipoDocumento.getItems().stream()
                            .filter(m -> m.getKey().equals(cliente.getTipoDocumento().name()))
                            .findFirst().orElse(null)
            );
        }

        idClienteCE = cliente.getIdCliente();
        limpiarError();
    }

    public void limpiarError() {
        List.of(txtDniruc, txtNombres, txtRepLegal, cbxTipoDocumento)
                .forEach(c -> {
                    if (c != null) {
                        c.getStyleClass().remove("text-field-error");
                        ttc.limpiarCampo(c);
                    }
                });
    }

    public void clearForm() {
        txtDniruc.clear();
        txtNombres.clear();
        txtRepLegal.clear();
        if (cbxTipoDocumento != null) cbxTipoDocumento.getSelectionModel().clearSelection();
        idClienteCE = 0L;
        limpiarError();
    }
}
