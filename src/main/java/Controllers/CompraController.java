package Controllers;

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
    private Button Comprar;


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

    int cantidadProducto = 0;
    int idProducto = 0;

    @FXML
    void btnPagar(ActionEvent event) {
        int cantidadtxt = Integer.parseInt(txtCantidad.getText());
        String valorId = txtIdProducto.getText();
        String valorCantidad = txtCantidad.getText();
        if (!(validacionCantidadId(valorCantidad) && validacionCantidadId(valorId))) {
            alertasValidacion("Los campos deben ser numeros positivos y mayores a 0 y no deben tener letras");
        }
        if (cantidadtxt > cantidadProducto) {
            String mensaje = "Cantidad insuficiente";
            alertasValidacion(mensaje);
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
    void btnAgregarProducto(ActionEvent event) {
        Producto p = tblProductos.getSelectionModel().getSelectedItem();
        if (p != null) {
            setearValores(p);
            Comprar.setDisable(false);
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
                Comprar.setDisable(false);

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
        txtCantidad.setEditable(true);
        cantidadProducto = p.getCantidad();
        idProducto = p.getId();

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
}
