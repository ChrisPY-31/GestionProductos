package Controllers;

import Modelo.Pedido;
import Modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaProductoController {

    @FXML
    private Text TituloProductos;

    @FXML
    private TableColumn<Pedido, String> colCategoria;

    @FXML
    private TableColumn<Pedido, Integer> colId;

    @FXML
    private TableColumn<Pedido, String> colNombre;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtCantidadNum;

    @FXML
    private TextField txtIdProducto;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtPrecioVenta;

    @FXML
    private TableView<Pedido> tblVerProductos;

    //importante esto es el id que lo llamamos en todas las ventanas

    public int idUsuario;
    public Pedido pedidoVenderUsuario;
    int cantidadUsuario;

    public void initializeProductos(Pedido pedidoUsuario) {
        System.out.println("ejecutnado esto");
        this.pedidoVenderUsuario = pedidoUsuario;
        this.cantidadUsuario = pedidoUsuario.getCantidadPedido();
        initializeVerProductos();
    }


    public void initializeVerProductos() {
        txtIdProducto.setText(String.valueOf(pedidoVenderUsuario.getId()));
        txtPrecio.setText(String.valueOf(pedidoVenderUsuario.getPrecio()));
        txtCantidad.setText(String.valueOf(pedidoVenderUsuario.getCantidadPedido()));
        txtPrecioVenta.setText(String.valueOf(pedidoVenderUsuario.getPrecioVenta()));
        txtPrecio.setEditable(false);
        txtCantidad.setEditable(false);
        txtPrecioVenta.setEditable(false);
        initializeTablas();
    }

    public void initializeTablas() {
        ObservableList<Pedido> obs = FXCollections.observableArrayList();
        obs.add(pedidoVenderUsuario);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePedido"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaPedido"));
        tblVerProductos.setItems(obs);

    }


    public void initializeBuscar(int id) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePedido"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaPedido"));

        Pedido pedido = new Pedido();
        ObservableList<Pedido> obs = pedido.getPedidosVender(id);
        tblVerProductos.setItems(obs);
        for (Pedido elemento : obs) {
            txtIdProducto.setText(String.valueOf(elemento.getId()));
            txtPrecio.setText(String.valueOf(elemento.getPrecio()));
            txtCantidad.setText(String.valueOf(elemento.getCantidadPedido()));
            txtPrecioVenta.setText(String.valueOf(elemento.getPrecioVenta()));
            cantidadUsuario = elemento.getCantidadPedido();
        }
    }


    @FXML
    void btnVenderProductos() {
        String idProductoPedido = txtIdProducto.getText();
        String cantidadVender = txtCantidadNum.getText();

        if (idProductoPedido.trim().isEmpty() || cantidadVender.trim().isEmpty()) {
            alertasValidacion("Seleccione un ID continuar");
            return;
        }
        if (!esNumeroPositivo(idProductoPedido) || !esNumeroPositivo(cantidadVender)) {
            alertasValidacion("El valor debe ser número positivo y no contener letras");
            return;
        }
        int cantidadNum = Integer.parseInt(cantidadVender);
        if (cantidadNum > cantidadUsuario) {
            alertasValidacion("Cantidad insuficiente: ");
            return;
        }
        try {
            int cantidadExistente = cantidadUsuario - cantidadNum;
            Pedido pedido = new Pedido();
            double precioNomal = Double.parseDouble(txtPrecio.getText());
            double precioVenta = Double.parseDouble(txtPrecioVenta.getText());
            double gananciasTotales = precioVenta-precioNomal;
            boolean response = pedido.actualizarCantidadesPedido(Integer.parseInt(idProductoPedido), cantidadExistente ,gananciasTotales);
            if(response){
                alertasCorrectas("Producto ventido correctamente");
                resetearValores();
            }
            else{
                alertasValidacion("Error al vender revisa bien las cosas chaval");
            }
        } catch (NumberFormatException e) {
            alertasValidacion("El ID del producto no es válido");
        }
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
    void buscarProductoVendido(ActionEvent event) {
        String idProducto = txtIdProducto.getText();
        String cantidadVender = txtCantidadNum.getText();

        // Validar campos vacíos
        if (idProducto.trim().isEmpty()) {
            alertasValidacion("Seleccione un ID continuar");
            return;
        }

        // Validar que ambos sean números positivos
        if (!esNumeroPositivo(idProducto)) {
            alertasValidacion("El valor debe ser número positivo y no contener letras");
            return;
        }

        // Intentar inicializar con el ID del producto
        try {
            initializeBuscar(Integer.parseInt(idProducto.trim()));
            txtCantidadNum.setEditable(true);
        } catch (NumberFormatException e) {
            alertasValidacion("El ID del producto no es válido");
            return;
        }

    }

    public boolean esNumeroPositivo(String texto) {
        try {
            int numero = Integer.parseInt(texto.trim());
            return numero > 0;
        } catch (NumberFormatException e) {
            return false;
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

    public void alertasValidacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validacion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void alertasCorrectas(String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Correcto");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void resetearValores(){
        txtIdProducto.setText("");
        txtCantidadNum.setText("");
        txtPrecioVenta.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }
}
