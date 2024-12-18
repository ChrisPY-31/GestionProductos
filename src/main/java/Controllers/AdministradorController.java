package Controllers;

import Modelo.Persona;
import Modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorController {

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, Integer> colCantidad;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private Button txtCerrarV;

    //este mete los valores a la tabla
    public void initialize() {

        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        this.colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        Producto producto = new Producto();
        ObservableList<Producto> items = producto.getProductos();
        this.tblProductos.setItems(items);
        tblProductos.refresh();
    }

    //este es la navegabilidad de la ventana Agregar Productos
    @FXML
    void btnVentanaAgregarProductos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/AgregarProductos.fxml"));
            Parent root = loader.load();
            AgregarProductosController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controller.btnInicio());
            stage.setOnCloseRequest(e -> controller.closeWindowsProductos());

            Stage myStage = (Stage) this.tblProductos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    //boton editar Productos
    @FXML
    void btnEditarProductos(ActionEvent event) {
        Producto p = tblProductos.getSelectionModel().getSelectedItem();
        boolean response = validacionDeBotones(p);
        if (!response) {

        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/AgregarProductos.fxml"));
                Parent root = loader.load();

                AgregarProductosController controller = loader.getController();
                controller.editarProducto(p);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                Stage myStage = (Stage) this.tblProductos.getScene().getWindow();
                myStage.close();
            } catch (IOException e) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    //boton eliminar Productos

    @FXML
    void btnEliminarProductos(ActionEvent event) {
        Producto p = tblProductos.getSelectionModel().getSelectedItem();
        boolean validation = validacionDeBotones(p);
        if (validation) {
            boolean response = p.EliminarProducto(p.getId());
            if (response) {
                ventanaAdministrar();
                alertas(response);
                p.getProductos();
            } else {
                alertas(response);
            }
        }
    }

    //Este cierra la sesion del administrador
    @FXML
    void btnCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/InicioSesion.fxml"));
            Parent root = loader.load();
            InicioSesionController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnRegister());
            stage.setOnCloseRequest(e -> controller.closeWindowsInicioSesion());

            Stage myStage = (Stage) this.txtCerrarV.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //Este es la navegabilidad de Adminsitrador
    public void ventanaAdministrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Administrador.fxml"));
            Parent root = loader.load();

            AdministradorController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage myStage = (Stage) this.tblProductos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    //alertas
    public void alertas(boolean response) {

        if (response) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Producto");
            alert.setHeaderText(null);
            alert.setContentText("Producto Eliminado Correctamente");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Producto");
            alert.setHeaderText(null);
            alert.setContentText("Error No se elimino el producto");
            alert.showAndWait();

        }
    }

    //validacion de botones
    public boolean validacionDeBotones(Producto producto) {
        if (producto == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un producto para editar o eliminar.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void closeWindowsAdministrador() {
        try {
            Stage myStage = (Stage) this.tblProductos.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
