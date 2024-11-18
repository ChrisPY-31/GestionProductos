package Controllers;

import Modelo.Pedido;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioController {

    @FXML
    private TableColumn<Pedido, Integer> colCantidad;

    @FXML
    private TableColumn<Pedido, String> colCategoria;

    @FXML
    private TableColumn<Pedido, Integer> colIdPedido;

    @FXML
    private TableColumn<Pedido, String> colNombre;

    @FXML
    private TableColumn<Pedido, Double> colPrecio;

    @FXML
    private TableColumn<Pedido, Double> colPrecioVenta;

    @FXML
    private TableView<Pedido> tblPedidos;


    @FXML
    private Text txtProductosTitulo;

    @FXML
    private Button txtCloseUsuario;

    //importante esto es el id que lo llamamos en todas las ventanas
    public int idUsuario;

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
        cargarProductos();
    }

    public void cargarProductos() {

        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePedido"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaPedido"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadPedido"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        Pedido pedido = new Pedido();
        ObservableList<Pedido> item = pedido.getPedidos(idUsuario);
        tblPedidos.setItems(item);
        tblPedidos.refresh();

    }

    @FXML
    void btnEditarPrecio(ActionEvent event) {

    }

    @FXML
    void btnFiltros(ActionEvent event) {

    }

    @FXML
    void btnVender(ActionEvent event) {

    }

    @FXML
    void btnVistaComprar() {
        //Navegabilidad de la vista Comprar comcluida
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/CompraProductos.fxml"));
            Parent root = loader.load();
            CompraController controller = loader.getController();
            controller.idUsuario = idUsuario;
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnInicio());
            stage.setOnCloseRequest(e -> controller.closeWindowsCompra());

            Stage myStage = (Stage) this.txtProductosTitulo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    //Navegabilidad de la vista VenderCompletada
    @FXML
    void btnVistaVender(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/VentaProducto.fxml"));
            Parent root = loader.load();
            VentaProductoController controller = loader.getController();
            controller.idUsuario = idUsuario;
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnVistaInicio());
            stage.setOnCloseRequest(e -> controller.closeWindowsVender());

            Stage myStage = (Stage) this.txtProductosTitulo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, e);

        }
    }


    @FXML
    void tblVerProductos(ActionEvent event) {

    }

    public void closeWindowsUser() {
        try {
            Stage myStage = (Stage) this.txtProductosTitulo.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void btnCerrarSesionUsuario(ActionEvent event) {
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

            Stage myStage = (Stage) this.txtCloseUsuario.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, e);

        }
    }



}

