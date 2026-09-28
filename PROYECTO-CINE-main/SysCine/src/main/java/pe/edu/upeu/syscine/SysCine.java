package pe.edu.upeu.syscine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pe.edu.upeu.syscine.config.AppContext;

import java.io.IOException;

public class SysCine extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AppContext context = AppContext.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/main_pelicula.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);

        Screen screen = Screen.getPrimary();
        Rectangle2D r2d = screen.getVisualBounds();
        Scene scene = new Scene(fxmlLoader.load(), r2d.getWidth(), r2d.getHeight() - 50);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("CineUPeU");
        stage.setScene(scene);
        stage.show();
    }
}
