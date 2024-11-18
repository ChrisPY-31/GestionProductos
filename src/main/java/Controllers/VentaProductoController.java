package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaProductoController {

    @FXML
    private Text TituloProductos;

    @FXML
    private TableColumn<?, ?> colCategoria;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtIdProducto;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtPrecioVenta;


    //importante esto es el id que lo llamamos en todas las ventanas

    public int idUsuario;


    @FXML
    void btnVenderProductos() {

    }

    @FXML
    void btnVistaComprar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/CompraProductos.fxml"));
            Parent root = loader.load();
            CompraController controller = loader.getController();
            controller.idUsuario = idUsuario;
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnVender());

            Stage myStage = (Stage) this.TituloProductos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(VentaProductoController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void btnVistaInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsUser());

            Stage myStage = (Stage) this.TituloProductos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(VentaProductoController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void closeWindowsVender() {
        try {
            Stage myStage = (Stage) this.TituloProductos.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(VentaProductoController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
