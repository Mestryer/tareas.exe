package pe.edu.upeu.sisventas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.NonNull;

import java.io.IOException;

public class Sysventas extends Application {
    @Override
    public void start(@NonNull Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/main-producto.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Screen screen=Screen.getPrimary();
        Rectangle2D r2d =screen.getVisualBounds();

        Scene scene = new Scene(fxmlLoader.load(), r2d .getWidth(), r2d.getHeight()-50);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
