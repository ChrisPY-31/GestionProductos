package Controllers;

import Modelo.Pedido;
import Modelo.Producto;
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
    private TextField txtCantidad;

    @FXML
    private TextField txtIdProducto;

    @FXML
    private TextField txtNombreProducto;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtCantidadComprar;

    @FXML
    private Button txtCerrarUsuario;

    @FXML
    private Button Comprar;

    //importante esto es el id que lo llamamos en todas las ventanas
    public int idUsuario;
    int cantidadComprar;

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


    int cantidadProducto = 0;
    int idProducto = 0;

    @FXML
    void btnPagar(ActionEvent event) {
        String valorId = txtIdProducto.getText();
        String valorCantidad = txtCantidadComprar.getText();
        if (!(validacionCantidadId(valorCantidad) && validacionCantidadId(valorId))) {
            alertasValidacion("Los campos deben ser numeros positivos y mayores a 0 y no deben tener letras");
            return;
        }
        int cantidadtxt = Integer.parseInt(txtCantidadComprar.getText());
        if (cantidadtxt > cantidadProducto) {
            String mensaje = "Cantidad insuficiente";
            alertasValidacion(mensaje);
            return;
        }


        cantidadComprar = Integer.parseInt(txtCantidadComprar.getText());
        Pedido pedido = new Pedido(idProducto, idUsuario, cantidadComprar);

        boolean response = pedido.AgregarPedidoProducto();
        if (response) {
            int cantidadExistente = cantidadProducto - cantidadComprar;
            alertasCorrectas("Producto agregado Correctamente");
            navegabilidadInicio(pedido);
            Producto producto = new Producto();
            boolean response1 = producto.actualizarCantidades(String.valueOf(idProducto), String.valueOf(cantidadExistente));
            System.out.println(response1);
        }

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

    @FXML
    void btnInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
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

    public void navegabilidadInicio(Pedido pedido) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            controller.setIdUsuario(pedido.getIdUsuario());
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
    void btnAgregarProducto(ActionEvent event) {
        Producto p = tblProductos.getSelectionModel().getSelectedItem();
        if (p != null) {
            setearValores(p);
        } else {
            String mensaje = "Por favor selecione un producto";
            alertasValidacion(mensaje);
        }

    }

    @FXML
    void btnBuscarProducto(ActionEvent event) {
        if (txtIdProducto.getText().equals("")) {
            String mensaje = "Selecione un producto";
            alertasValidacion(mensaje);
        } else {
            int id = Integer.parseInt(txtIdProducto.getText());
            Producto producto = new Producto();
            boolean responseProductos = producto.buscarProducto(id);
            if (responseProductos) {
                setearValores(producto);

            } else {
                String mensaje = "No se encontro el producto con el id: " + id;
                reinicarValores();
                alertasValidacion(mensaje);
            }
        }
    }

    public void setearValores(Producto p) {
        txtIdProducto.setText(String.valueOf(p.getId()));
        txtNombreProducto.setText(p.getNombre());
        txtCantidad.setText(String.valueOf(p.getCantidad()));
        txtTotal.setText(String.valueOf(p.getPrecio() * p.getCantidad()));
        cantidadProducto = Integer.parseInt(txtCantidad.getText());
        idProducto = p.getId();
        cantidadComprar = p.getCantidad();
        Comprar.setDisable(false);

    }

    public void reinicarValores() {
        txtIdProducto.setText("");
        txtNombreProducto.setText("");
        txtCantidad.setText("");
        txtTotal.setText("");
    }

    public void closeWindowsCompra() {
        try {
            Stage myStage = (Stage) this.txtCompraTitulo.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void alertasValidacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    public boolean validacionCantidadId(String atributo) {
        try {
            int valor = Integer.parseInt(atributo);
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void alertasCorrectas(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Correcto");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void btnCerrarSesionUser(ActionEvent event) {
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

            Stage myStage = (Stage) this.txtCerrarUsuario.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(CompraController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
