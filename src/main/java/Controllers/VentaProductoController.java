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
import javafx.scene.control.*;
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
    private Button btnAgregarProducto;

    @FXML
    private TableColumn<Pedido, Integer> colId;

    @FXML
    private TableColumn<Pedido, String> colNombre;

    @FXML
    private TableColumn<Pedido, Integer> colCantidad;

    @FXML
    private TableColumn<Pedido, Double> colPrecioVenta;

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
    private TextField txtNombre;

    @FXML
    private TextField txtTotalProductos;

    @FXML
    private TextField txtGananciasDelDia;

    @FXML
    private TableView<Pedido> tblVerProductos;

    //importante esto es el id que lo llamamos en todas las ventanas

    public int idUsuario;
    public Pedido pedidoVenderUsuario;
    int cantidadUsuario;
    double precioAcoumuladoTotal = 0;

    //este viene desde usuario
    public void initializeProductos(Pedido pedidoUsuario) {
        System.out.println("ejecutnado esto");
        this.pedidoVenderUsuario = pedidoUsuario;
        this.cantidadUsuario = pedidoUsuario.getCantidadPedido();
        initializeVerProductos();
    }


    public void initializeVerProductos() {
        txtIdProducto.setText(String.valueOf(pedidoVenderUsuario.getId()));
        txtNombre.setText(pedidoVenderUsuario.getNombrePedido());
        txtPrecio.setText(String.valueOf(pedidoVenderUsuario.getPrecio()));
        txtCantidad.setText(String.valueOf(pedidoVenderUsuario.getCantidadPedido()));
        txtPrecioVenta.setText(String.valueOf(pedidoVenderUsuario.getPrecioVenta()));
        txtPrecio.setEditable(false);
        txtCantidad.setEditable(false);
        txtPrecioVenta.setEditable(false);
        //initializeTablas();
    }

    private ObservableList<Pedido> obs;


    public void initialize() {
        obs = FXCollections.observableArrayList();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePedido"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadPedido"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        tblVerProductos.setItems(obs);

        Pedido pedido = new Pedido();
        String ganaciasDelDia = pedido.getTotalPedidos();
        txtGananciasDelDia.setText(ganaciasDelDia);
    }


    public void initializeBuscar(int id) {

        Pedido pedido = new Pedido();
        ObservableList<Pedido> obs = pedido.getPedidosVender(id);
        for (Pedido elemento : obs) {
            txtIdProducto.setText(String.valueOf(elemento.getId()));
            txtNombre.setText(elemento.getNombrePedido());
            txtPrecio.setText(String.valueOf(elemento.getPrecio()));
            txtCantidad.setText(String.valueOf(elemento.getCantidadPedido()));
            txtPrecioVenta.setText(String.valueOf(elemento.getPrecioVenta()));
            cantidadUsuario = elemento.getCantidadPedido();
        }
    }


    @FXML
    void btnVenderProductos() {
        for (Pedido p : obs) {
            int cantidadExistencia = p.getCantidad() - p.getCantidadPedido();
            double precioProductoIndividual = p.getPrecioVenta() - p.getPrecio();
            double precioTotalProducto = precioProductoIndividual * p.getCantidadPedido();

            System.out.println(precioProductoIndividual + "desde vender");
            Pedido pedido = new Pedido();
            boolean response = pedido.actualizarCantidadesPedido(p.getId(), cantidadExistencia, precioTotalProducto);
            if (!response) {
                alertasValidacion("Error productos no vendidos por favor revise la venta");
                return;
            }
        }
        alertasCorrectas("Producto Vendido correctamente");
        tblVerProductos.getItems().clear();

    }


    @FXML
    void AgregarProductoVendido(ActionEvent event) {

        if (txtIdProducto.getText().equals("") || txtCantidadNum.getText().equals("")) {
            alertasValidacion("El id y la cantidad no pueden ir vacios");
            return;
        }

        if (Integer.parseInt(txtCantidadNum.getText()) > Integer.parseInt(txtCantidad.getText())) {
            alertasValidacion("Cantidad insuficiente");
            return;
        }

        if (!esNumeroPositivo(txtIdProducto.getText()) || !esNumeroPositivo(txtCantidadNum.getText())) {
            alertasValidacion("El id y la cantidad no pueden ser negativos");
        }

        if (!esEntero(txtCantidadNum.getText())) {
            alertasValidacion("la cantidad debe ser entero");
            return;
        }

        int idPedido = Integer.parseInt(txtIdProducto.getText());
        String nombre = txtNombre.getText();
        int cantidadPedido = Integer.parseInt(txtCantidadNum.getText());
        double precioVenta = Double.parseDouble(txtPrecioVenta.getText());
        double precioNormal = Double.parseDouble(txtPrecio.getText());
        int cantidadProductoOrignal = Integer.parseInt(txtCantidad.getText());

        Pedido pedido = new Pedido(idPedido, nombre, cantidadPedido, precioNormal, precioVenta, cantidadProductoOrignal);
        if (!this.obs.contains(pedido)) {
            double precioTotal = pedido.getPrecioVenta() - Double.parseDouble(txtPrecio.getText());
            precioAcoumuladoTotal = precioAcoumuladoTotal + (precioTotal * cantidadPedido);
            txtTotalProductos.setText(String.valueOf(precioAcoumuladoTotal));
            this.obs.add(pedido);
            resetearValores();
        }

    }

    @FXML
    void ElilminarProductoVendido(ActionEvent event) {
        Pedido p = tblVerProductos.getSelectionModel().getSelectedItem();

        if (p == null) {
            alertasValidacion("Seleccione un producto");
        }
        else{
            obs.remove(p);
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

    //buscar prodcutos
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
            btnAgregarProducto.setDisable(false);
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

    public void alertasCorrectas(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Correcto");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void resetearValores() {
        txtIdProducto.setText("");
        txtNombre.setText("");
        txtCantidadNum.setText("");
        txtPrecioVenta.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }

    public boolean esEntero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
