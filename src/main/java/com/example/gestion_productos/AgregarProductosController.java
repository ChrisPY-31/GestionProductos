package com.example.gestion_productos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AgregarProductosController {

    @FXML
    private Label txtAgregar;

    @FXML
    private TextField txtCantidad;

    @FXML
    private Button txtInicio;

    @FXML
    private TextField txtnombreProducto;

    @FXML
    private TextField txtprecio;

    @FXML
    void AgregarProducto(ActionEvent event) {

    }

    @FXML
    void ElegirCategoria(ActionEvent event) {

    }

    @FXML
    void Inicio(ActionEvent event) {

    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            HelloController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.txtAgregar.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
