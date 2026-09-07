package com.example.butterflies;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Label message = new Label("welcome to javaFX");
        message.setStyle ("-fx-front-size; 18pz");
        Button boton =new Button("saludar");
        boton.setOnAction (evento ->{
             message.setText("hello from javaFX");
        });
        VBox raiz =new VBox(15, message, boton);
        raiz.setAlignment(Pos.CENTER);
        raiz.setStyle("-fx-padding: 30;");
        Scene escena = new Scene(raiz,400,250);
        stage.setTitle("hello javaFX");
        stage.setScene(escena);
        stage.show();

    }
}
