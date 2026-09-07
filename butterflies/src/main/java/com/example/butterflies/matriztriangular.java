package com.example.butterflies;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class matriztriangular extends Application {

    static GridPane grid;
    Label lbinto;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start (Stage primaryStage){
        Label titulo = new Label("matriz de la forma 5");

        Label lbtam=new Label("tamaño (n): ");
        Spinner<Integer>spInicio=new Spinner<>(2,30,5);

        HBox controles = new HBox(10,lbtam,spInicio);
        grid= new GridPane();
        grid.setHgap(3);
        grid.setVgap(3);

        VBox root =new VBox(15,controles,grid);
        root.setPadding(new Insets(15));
        ScrollPane scroll =new ScrollPane(root);

        primaryStage.setScene(new Scene(scroll));
        primaryStage.setTitle("ejemplos de matriz GUI");
        primaryStage.show();
    }

    public void  matriz5(int tam, int num1){
        grid.getChildren().clear();
        for(int f=0; f<tam;f++){
            for (int c=tam-1; c>tam-1-f; c++){
                Button cuadrito = new Button(String.valueOf(num1));
                cuadrito.setMinSize(48,42);
                cuadrito.setPrefSize(48,42);
                grid.add(cuadrito,c,f);
                int ff= f;
                int cc= c;
                cuadrito.setOnAction(event -> {
                lbinto.setText("su valor es: " + cuadrito.getText() + "ubicado en la fila" + (ff) + "y columna; " +(cc));
                  });
                num1++;
            }
        }
    }
}
