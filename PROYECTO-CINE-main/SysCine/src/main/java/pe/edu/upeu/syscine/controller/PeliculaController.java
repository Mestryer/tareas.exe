package pe.edu.upeu.syscine.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import pe.edu.upeu.syscine.components.ColumnInfo;
import pe.edu.upeu.syscine.components.TableViewHelper;
import pe.edu.upeu.syscine.components.Toast;
import pe.edu.upeu.syscine.components.ToltipCustom;
import pe.edu.upeu.syscine.dto.ComboBoxOption;
import pe.edu.upeu.syscine.enums.TipoPelicula;
import pe.edu.upeu.syscine.enums.MetodoPago;
import pe.edu.upeu.syscine.exception.ModelNotFoundException;
import pe.edu.upeu.syscine.model.Opinion;
import pe.edu.upeu.syscine.model.Pelicula;
import pe.edu.upeu.syscine.model.Venta;
import pe.edu.upeu.syscine.service.IFormatoService;
import pe.edu.upeu.syscine.service.IGeneroService;
import pe.edu.upeu.syscine.service.IOpinionService;
import pe.edu.upeu.syscine.service.IPeliculaService;
import pe.edu.upeu.syscine.service.ISalaService;
import pe.edu.upeu.syscine.service.IVentaService;

import java.util.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class PeliculaController {
    private final ISalaService ss;
    private final IGeneroService gs;
    private final IPeliculaService ps;
    private final IFormatoService fs;
    private final IOpinionService os;
    private final IVentaService vs;

    @FXML ComboBox<ComboBoxOption> cbxTipoPelicula;
    @FXML ComboBox<ComboBoxOption> cbxGenero, cbxSala, cbxFormato;
    @FXML TextField txtNombrePelicula, txtPrecio,
            txtPrecioOld, txtDuracion, txtBoletosDisp, txtBoletosVend, txtFiltroDato;

    @FXML private TableView<Pelicula> tableView;
    ObservableList<Pelicula> listarPelicula;

    Pelicula formulario;
    Long idPeliculaCE = 0L;

    @FXML
    Label lbnMsg;
    @FXML private AnchorPane miContenedor;
    Stage stage;
    private Validator validator;
    private final ToltipCustom ttc = new ToltipCustom();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) miContenedor.getScene().getWindow();
        });

        cbxTipoPelicula.getItems().addAll(ps.listarTipoPelicula());
        cbxGenero.getItems().addAll(gs.listarCombobox());
        cbxSala.getItems().addAll(ss.listarCombobox());
        cbxFormato.getItems().addAll(fs.listarCombobox());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        TableViewHelper<Pelicula> tableViewHelper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columns = new LinkedHashMap<>();
        columns.put("ID", new ColumnInfo("idPelicula", 50.0));
        columns.put("Titulo", new ColumnInfo("nombre", 180.0));
        columns.put("Tipo", new ColumnInfo("tipoPelicula", 110.0));
        columns.put("Genero", new ColumnInfo("idGenero.nombre", 120.0));
        columns.put("Sala", new ColumnInfo("idSala.nombre", 90.0));
        columns.put("Formato", new ColumnInfo("idFormato.nombreFormato", 80.0));
        columns.put("Duracion(min)", new ColumnInfo("duracionMin", 100.0));
        columns.put("Precio S/.", new ColumnInfo("precioEntrada", 90.0));
        columns.put("Boletos Disp.", new ColumnInfo("boletosDisponibles", 100.0));

        Consumer<Pelicula> updateAction = p -> {
            editForm(p);
            idPeliculaCE = p.getIdPelicula();
        };
        Consumer<Pelicula> deleteAction = p -> {
            ps.delete(p.getIdPelicula());
            Stage st = (Stage) miContenedor.getScene().getWindow();
            double w = st.getWidth() / 1.5, h = st.getHeight() / 2;
            Toast.showToast(st, "Se elimino correctamente!!", 2000, w, h);
            listar();
        };

        LinkedHashMap<String, Consumer<Pelicula>> extraActions = null;

        tableViewHelper.addColumnsInOrderWithSizeExtra(tableView, columns, updateAction, deleteAction, extraActions);
        activarIconoOpinionEnTitulo();
        tableView.setTableMenuButtonVisible(true);

        if (txtFiltroDato != null) {
            txtFiltroDato.textProperty().addListener((obs, oldV, newV) -> buscarPelicula());
        }

        listar();
    }

    public void listar() {
        try {
            tableView.getItems().clear();
            listarPelicula = FXCollections.observableArrayList(ps.findAll());
            tableView.getItems().addAll(listarPelicula);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // -----------------------------------------------------------------
    //  ICONO DE OPINION JUNTO AL TITULO DE LA PELICULA
    // -----------------------------------------------------------------
    @SuppressWarnings("unchecked")
    private void activarIconoOpinionEnTitulo() {
        TableColumn<Pelicula, Object> colTitulo = (TableColumn<Pelicula, Object>) tableView.getColumns().get(1);
        colTitulo.setCellFactory(col -> new TableCell<Pelicula, Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                Pelicula pelicula = getTableView().getItems().get(getIndex());
                Button btnOpinion = new Button("\u2605");
                btnOpinion.getStyleClass().add("btn-icon-opinion");
                Tooltip.install(btnOpinion, new Tooltip("Ver / dejar opiniones"));
                btnOpinion.setOnAction(e -> mostrarOpiniones(pelicula));
                Label lbl = new Label(item.toString());
                HBox box = new HBox(6, btnOpinion, lbl);
                box.setAlignment(Pos.CENTER_LEFT);
                setGraphic(box);
                setText(null);
            }
        });
    }

    @FXML
    public void buscarPelicula() {
        String texto = txtFiltroDato == null ? "" : txtFiltroDato.getText();
        tableView.getItems().clear();
        tableView.getItems().addAll(ps.buscarPorNombre(texto));
    }

    @FXML
    public void validarFormulario() {
        formulario = new Pelicula();
        formulario.setNombre(txtNombrePelicula.getText());
        formulario.setPrecioEntrada(parseDoubleSafe(txtPrecio.getText()));
        formulario.setPrecioEntradaOld(parseDoubleSafe(txtPrecioOld.getText()));
        formulario.setDuracionMin(parseDoubleSafe(txtDuracion.getText()));
        formulario.setBoletosDisponibles(parseDoubleSafe(txtBoletosDisp.getText()));
        formulario.setBoletosVendidos(parseDoubleSafe(txtBoletosVend.getText()));

        String idxTP = cbxTipoPelicula.getSelectionModel().getSelectedItem() == null ? ""
                : cbxTipoPelicula.getSelectionModel().getSelectedItem().getKey();
        formulario.setTipoPelicula(idxTP.equals("") ? null : TipoPelicula.valueOf(idxTP));

        String idxG = cbxGenero.getSelectionModel().getSelectedItem() == null ? "0"
                : cbxGenero.getSelectionModel().getSelectedItem().getKey();
        formulario.setIdGenero(idxG.equals("0") ? null : gs.findById(Long.parseLong(idxG)));

        String idxS = cbxSala.getSelectionModel().getSelectedItem() == null ? "0"
                : cbxSala.getSelectionModel().getSelectedItem().getKey();
        formulario.setIdSala(idxS.equals("0") ? null : ss.findById(Long.parseLong(idxS)));

        String idxF = cbxFormato.getSelectionModel().getSelectedItem() == null ? "0"
                : cbxFormato.getSelectionModel().getSelectedItem().getKey();
        formulario.setIdFormato(idxF.equals("0") ? null : fs.findById(Long.parseLong(idxF)));

        Set<ConstraintViolation<Pelicula>> violaciones = validator.validate(formulario);
        List<ConstraintViolation<Pelicula>> violacionesOrdenadas = violaciones.stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString())).toList();

        if (violacionesOrdenadas.isEmpty()) {
            procesarFormulario();
        } else {
            mostrarErroresValidacion(violacionesOrdenadas);
        }
    }

    private void mostrarErroresValidacion(List<ConstraintViolation<Pelicula>> violaciones) {
        limpiarError();
        Map<String, Control> campos = new LinkedHashMap<>();
        campos.put("nombre", txtNombrePelicula);
        campos.put("tipoPelicula", cbxTipoPelicula);
        campos.put("precioEntrada", txtPrecio);
        campos.put("precioEntradaOld", txtPrecioOld);
        campos.put("duracionMin", txtDuracion);
        campos.put("boletosDisponibles", txtBoletosDisp);
        campos.put("boletosVendidos", txtBoletosVend);
        campos.put("idGenero", cbxGenero);
        campos.put("idSala", cbxSala);
        campos.put("idFormato", cbxFormato);

        LinkedHashMap<String, String> erroresOrdenados = new LinkedHashMap<>();
        final Control[] primerCtrl = {null};
        for (String campo : campos.keySet()) {
            violaciones.stream()
                    .filter(v -> v.getPropertyPath().toString().equals(campo))
                    .findFirst().ifPresent(v -> {
                        erroresOrdenados.put(campo, v.getMessage());
                        Control c = campos.get(campo);
                        if (c != null && !c.getStyleClass().contains("text-field-error")) {
                            ttc.marcarError(c, v.getMessage().trim());
                        }
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
        Stage st = (Stage) miContenedor.getScene().getWindow();
        limpiarError();
        double w = st.getWidth() / 1.5, h = st.getHeight() / 2;
        if (idPeliculaCE > 0L) {
            formulario.setIdPelicula(idPeliculaCE);
            ps.update(idPeliculaCE, formulario);
            Toast.showToast(st, "Se actualizo correctamente!!", 2000, w, h);
        } else {
            ps.save(formulario);
            Toast.showToast(st, "Se guardo correctamente!!", 2000, w, h);
        }
        clearForm();
        listar();
    }

    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void editForm(Pelicula pelicula) {
        txtNombrePelicula.setText(pelicula.getNombre());
        txtPrecio.setText(pelicula.getPrecioEntrada().toString());
        txtPrecioOld.setText(pelicula.getPrecioEntradaOld().toString());
        txtDuracion.setText(pelicula.getDuracionMin().toString());
        txtBoletosDisp.setText(pelicula.getBoletosDisponibles().toString());
        txtBoletosVend.setText(pelicula.getBoletosVendidos().toString());

        cbxTipoPelicula.getSelectionModel().select(
                cbxTipoPelicula.getItems().stream()
                        .filter(m -> m.getKey().equals(pelicula.getTipoPelicula().name()))
                        .findFirst().orElse(null));

        cbxGenero.getSelectionModel().select(
                cbxGenero.getItems().stream()
                        .filter(g -> Long.parseLong(g.getKey()) == pelicula.getIdGenero().getIdGenero())
                        .findFirst().orElse(null));
        cbxSala.getSelectionModel().select(
                cbxSala.getItems().stream()
                        .filter(s -> Long.parseLong(s.getKey()) == pelicula.getIdSala().getIdSala())
                        .findFirst().orElse(null));
        cbxFormato.getSelectionModel().select(
                cbxFormato.getItems().stream()
                        .filter(f -> Long.parseLong(f.getKey()) == pelicula.getIdFormato().getIdFormato())
                        .findFirst().orElse(null));
        idPeliculaCE = pelicula.getIdPelicula();
        limpiarError();
    }

    public void limpiarError() {
        List.of(txtNombrePelicula, cbxTipoPelicula, txtPrecio, txtPrecioOld, txtDuracion,
                        txtBoletosDisp, txtBoletosVend, cbxGenero, cbxSala, cbxFormato)
                .forEach(c -> {
                    c.getStyleClass().remove("text-field-error");
                    ttc.limpiarCampo(c);
                });
    }

    public void clearForm() {
        txtNombrePelicula.clear();
        cbxTipoPelicula.getSelectionModel().clearSelection();
        txtPrecio.clear();
        txtPrecioOld.clear();
        txtDuracion.clear();
        txtBoletosDisp.clear();
        txtBoletosVend.clear();
        cbxGenero.getSelectionModel().clearSelection();
        cbxSala.getSelectionModel().clearSelection();
        cbxFormato.getSelectionModel().clearSelection();
        idPeliculaCE = 0L;
        limpiarError();
    }

    // -----------------------------------------------------------------
    //  OPINIONES DE LA PELICULA
    // -----------------------------------------------------------------
    private void mostrarOpiniones(Pelicula pelicula) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Opiniones - " + pelicula.getNombre());
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label lblPromedio = new Label();
        ListView<String> listaOpiniones = new ListView<>();
        listaOpiniones.setPrefHeight(160);

        Runnable refrescar = () -> {
            List<Opinion> opiniones = os.listarPorPelicula(pelicula.getIdPelicula());
            listaOpiniones.getItems().clear();
            for (Opinion o : opiniones) {
                listaOpiniones.getItems().add(
                        o.getUsuario() + " (" + o.getPuntuacion() + "/5): " + o.getComentario());
            }
            double promedio = os.promedioPuntuacion(pelicula.getIdPelicula());
            lblPromedio.setText(String.format("Calificacion promedio: %.1f / 5  (%d opiniones)",
                    promedio, opiniones.size()));
        };
        refrescar.run();

        Separator sep = new Separator();

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Tu nombre");
        ComboBox<Integer> cbxPuntuacion = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        cbxPuntuacion.setPromptText("Puntuacion (1-5)");
        TextArea txtComentario = new TextArea();
        txtComentario.setPromptText("Escribe tu opinion sobre la pelicula...");
        txtComentario.setPrefRowCount(3);

        Button btnAgregar = new Button("Agregar opinion");
        Label lblAviso = new Label();
        lblAviso.setStyle("-fx-text-fill: red;");

        btnAgregar.setOnAction(e -> {
            if (txtUsuario.getText() == null || txtUsuario.getText().isBlank()
                    || cbxPuntuacion.getValue() == null
                    || txtComentario.getText() == null || txtComentario.getText().isBlank()) {
                lblAviso.setText("Completa tu nombre, puntuacion y comentario.");
                return;
            }
            lblAviso.setText("");
            Opinion nueva = new Opinion();
            nueva.setIdPelicula(pelicula.getIdPelicula());
            nueva.setUsuario(txtUsuario.getText().trim());
            nueva.setPuntuacion(cbxPuntuacion.getValue());
            nueva.setComentario(txtComentario.getText().trim());
            os.save(nueva);

            txtUsuario.clear();
            cbxPuntuacion.getSelectionModel().clearSelection();
            txtComentario.clear();
            refrescar.run();
        });

        HBox filaForm = new HBox(8, txtUsuario, cbxPuntuacion);
        root.getChildren().addAll(lblPromedio, listaOpiniones, sep,
                new Label("Dejar una opinion:"), filaForm, txtComentario, btnAgregar, lblAviso);

        dialog.getDialogPane().setContent(root);
        dialog.getDialogPane().setPrefWidth(420);
        dialog.showAndWait();
    }

    // -----------------------------------------------------------------
    //  COMPRA DE BOLETOS
    // -----------------------------------------------------------------
    @FXML
    public void comprarBoletosSeleccionada() {
        Pelicula seleccionada = tableView.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una pelicula de la tabla para comprar boletos.");
            return;
        }
        comprarBoletos(seleccionada);
    }

    private void comprarBoletos(Pelicula pelicula) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Comprar boletos");
        dialog.setHeaderText(pelicula.getNombre() + "  -  Disponibles: "
                + pelicula.getBoletosDisponibles().intValue()
                + "  -  Precio: S/. " + pelicula.getPrecioEntrada());

        ButtonType btnComprar = new ButtonType("Comprar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnComprar, ButtonType.CANCEL);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TextField txtCantidad = new TextField("1");
        TextField txtDni = new TextField();
        txtDni.setPromptText("DNI del cliente (8 digitos)");
        ComboBox<MetodoPago> cbxMetodoPago = new ComboBox<>(FXCollections.observableArrayList(MetodoPago.values()));
        cbxMetodoPago.setPromptText("Selecciona un metodo de pago");
        cbxMetodoPago.setMaxWidth(Double.MAX_VALUE);

        Label lblAviso = new Label();
        lblAviso.setStyle("-fx-text-fill: red;");

        root.getChildren().addAll(
                new Label("Cantidad de boletos:"), txtCantidad,
                new Label("DNI del cliente:"), txtDni,
                new Label("Metodo de pago:"), cbxMetodoPago,
                lblAviso);

        dialog.getDialogPane().setContent(root);
        dialog.getDialogPane().setPrefWidth(340);

        // Validamos antes de dejar cerrar el dialogo con "Comprar"
        Button botonComprar = (Button) dialog.getDialogPane().lookupButton(btnComprar);
        botonComprar.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            String errorTexto = validarDatosCompra(txtCantidad.getText(), txtDni.getText(), cbxMetodoPago.getValue());
            if (errorTexto != null) {
                lblAviso.setText(errorTexto);
                event.consume();
            }
        });

        Optional<ButtonType> resultado = dialog.showAndWait();
        if (resultado.isPresent() && resultado.get() == btnComprar) {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            String dni = txtDni.getText().trim();
            MetodoPago metodoPago = cbxMetodoPago.getValue();

            try {
                Pelicula actualizada = ps.comprarBoletos(pelicula.getIdPelicula(), cantidad);
                double total = cantidad * actualizada.getPrecioEntrada();

                Venta venta = new Venta();
                venta.setIdPelicula(pelicula.getIdPelicula());
                venta.setNombrePelicula(pelicula.getNombre());
                venta.setDniCliente(dni);
                venta.setMetodoPago(metodoPago);
                venta.setCantidadBoletos(cantidad);
                venta.setTotal(total);
                vs.save(venta);

                Stage st = (Stage) miContenedor.getScene().getWindow();
                double w = st.getWidth() / 1.5, h = st.getHeight() / 2;
                Toast.showToast(st, String.format(
                        "Compra realizada: %d boleto(s) - DNI %s - %s - Total S/. %.2f",
                        cantidad, dni, metodoPago.getDescripcion(), total), 3000, w, h);
                listar();
            } catch (ModelNotFoundException ex) {
                mostrarAlerta(ex.getMessage());
            }
        }
    }

    private String validarDatosCompra(String cantidadTexto, String dni, MetodoPago metodoPago) {
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadTexto == null ? "" : cantidadTexto.trim());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            return "Ingresa una cantidad de boletos valida.";
        }
        if (dni == null || !dni.trim().matches("\\d{8}")) {
            return "Ingresa un DNI valido (8 digitos).";
        }
        if (metodoPago == null) {
            return "Selecciona un metodo de pago.";
        }
        return null;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
