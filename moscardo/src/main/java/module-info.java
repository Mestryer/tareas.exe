module com.example.moscardo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.moscardo to javafx.fxml;
    exports com.example.moscardo;
}