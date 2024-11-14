package Controllers;

import Modelo.Producto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompraController {

    @FXML
    private TableColumn<Producto, Integer> colCantidad;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private Text txtCompraTitulo;

    @FXML
    private TextField txtIdCantidad;

    @FXML
    private TextField txtIdProducto;

    @FXML
    private TextField txtTotal;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));

        Producto producto = new Producto();
        ObservableList<Producto> obs = producto.getProductos();
        tblProductos.setItems(obs);
    }


    @FXML
    void btnInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnVistaComprar());
            stage.setOnCloseRequest(e -> controller.closeWindowsUser());

            Stage myStage = (Stage) this.txtCompraTitulo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    @FXML
    void btnPagar(ActionEvent event) {

    }

    @FXML
    void btnVender() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/VentaProducto.fxml"));
            Parent root = loader.load();
            VentaProductoController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnVenderProductos());

            Stage myStage = (Stage) this.txtCompraTitulo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(CompraController.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    public void closeWindowsCompra() {
        try {
            Stage myStage = (Stage) this.txtCompraTitulo.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
