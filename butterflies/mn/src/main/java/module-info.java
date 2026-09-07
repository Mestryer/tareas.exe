module pe.edu.upeu.mn {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pe.edu.upeu.mn to javafx.fxml;
    exports pe.edu.upeu.mn;
}