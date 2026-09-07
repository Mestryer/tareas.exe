module com.example.butterflies {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.butterflies to javafx.fxml;
    exports com.example.butterflies;
}