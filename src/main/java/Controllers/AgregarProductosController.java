package Controllers;

import Modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;

public class AgregarProductosController implements Initializable {

    @FXML
    private Button GuardarProducto;

    @FXML
    private Label txtAgregar;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtNombreProducto;

    @FXML
    private TextField txtPrecio;

    @FXML
    private ComboBox<String> CategoriaList;

    @FXML
    void ElegirCategoria(ActionEvent event) {

    }

    @FXML
    void btnAgregarProducto(ActionEvent event) {
        String nombre = txtNombreProducto.getText();
        String categoria = CategoriaList.getSelectionModel().getSelectedItem();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        double precio = Double.parseDouble(txtPrecio.getText());

        Producto producto = new Producto(nombre, categoria, cantidad, precio);

        boolean response = producto.AgregarProducto();
        alertas(response);
        restablecesAtributes();
        btnInicio();
    }

    @FXML
    void btnInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Administrador.fxml"));
            Parent root = loader.load();
            AdministradorController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnVentanaAgregarProductos());
            stage.setOnCloseRequest(e -> controller.closeWindowsAdministrador());

            Stage myStage = (Stage) this.txtAgregar.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void closeWindowsProductos() {
        try {
            Stage myStage = (Stage) this.txtAgregar.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Alimentos", "Bebidas", "Salud", "Belleza", "Hogar");
        CategoriaList.setItems(list);
    }

    public void alertas(boolean response) {
        System.out.println("Entro a alertas");
        if (response) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Producto");
            alert.setHeaderText(null);
            alert.setContentText("producto agregado correctamente");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Producto");
            alert.setHeaderText(null);
            alert.setContentText("Error al agregar un producto");
            alert.showAndWait();

        }
    }

    public void restablecesAtributes(){
        txtNombreProducto.setText("");
        CategoriaList.getSelectionModel().clearSelection();
        txtCantidad.setText("");
        txtPrecio.setText("");
    }

}


