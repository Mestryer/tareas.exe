module pe.edu.upeu.sisventas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires static lombok;
    requires jakarta.validation;
    requires java.desktop;

    opens pe.edu.upeu.sisventas.controller to javafx.fxml;
    exports pe.edu.upeu.sisventas;
}