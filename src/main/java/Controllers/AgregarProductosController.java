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
    private Button AgregarProducto;
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
    private Label lblMensajePrecio;

    @FXML
    private Label lblMensajeCantidad;

    @FXML
    private Label lblSeleccionaCategoria;

    @FXML
    void ElegirCategoria(ActionEvent event) {

    }

    int id = 0;

    //este agrega o actualiza productos
    @FXML
    void btnAgregarProducto() {
        if (txtNombreProducto.getText().equals("") || txtPrecio.getText().equals("") || CategoriaList.getSelectionModel().getSelectedItem() == null || txtCantidad.getText().equals("")) {
            alertasdelFormulario();
            return ;
        }
        if (Integer.parseInt(txtCantidad.getText()) < 1 || Integer.parseInt(txtPrecio.getText()) < 1) {
            String mensaje = "la cantidad o el precion o puede ser negativa ni debe ser ceros";
            mostrarAlerta(mensaje);
            return ;
        }
            responseEditarAgregar(id);

    }

    //aqui recibe el id si el id = 0 agrega un producto nuevo
    //si el id es mayor a 0 es porque se va editar el producto con ese id
    public void responseEditarAgregar(int id) {
        if (id == 0) {
            boolean responseValidacion = validaciondeCantidadPrecio();
            if (responseValidacion) {
                String nombre = txtNombreProducto.getText();
                String categoria = CategoriaList.getSelectionModel().getSelectedItem();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precio = Double.parseDouble(txtPrecio.getText());
                Producto producto = new Producto(nombre, categoria, cantidad, precio);
                boolean response = producto.AgregarProducto();
                alertas(response, producto.getId());
                restablecesAtributes();
                btnInicio();

            }
        }
        if (id > 0) {
            boolean responseValidacion = validaciondeCantidadPrecio();
            if (responseValidacion) {
                String nombre = txtNombreProducto.getText();
                String categoria = CategoriaList.getSelectionModel().getSelectedItem();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precio = Double.parseDouble(txtPrecio.getText());
                Producto producto = new Producto(id, nombre, categoria, cantidad, precio);

                boolean response = producto.ActualizarProductos();

                alertas(response, id);
                restablecesAtributes();
                AgregarProducto.setText("Agregar Producto");
                btnInicio();
            }

        }
    }

    //validacion de para que sea precio = decimal cantidad = entero;
    public boolean validaciondeCantidadPrecio() {
        String cantidadTexto = txtCantidad.getText();
        String precioTexto = txtPrecio.getText();

        if (!esEntero(cantidadTexto)) {
            mostrarAlerta("Cantidad debe ser un número entero.");
            return false;
        }

        if (!esDecimal(precioTexto)) {
            mostrarAlerta("Precio debe ser un número con decimales.");
            return false;
        }
        return true;
    }

    //nos trae el producto extraemos su id para saber si estamos editando o agregando productos
    public void editarProducto(Producto producto) {
        id = producto.getId();
        atributoSeleccionadoEditado(producto);
        AgregarProducto.setText("Actualizar Producto");
    }

    //este es la navegabilidad nos manda a la ventana inicio
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

        txtPrecio.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Cuando pierde el foco
                lblMensajePrecio.setVisible(false); // Ocultar el mensaje
            }
        });

        txtPrecio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                lblMensajePrecio.setVisible(true); // Mostrar mensaje mientras escribe
            } else {
                lblMensajePrecio.setVisible(false); // Ocultar mensaje si está vacío
            }
        });

        txtCantidad.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Cuando pierde el foco
                lblMensajeCantidad.setVisible(false); // Ocultar el mensaje
            }
        });

        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                lblMensajeCantidad.setVisible(true); // Mostrar mensaje mientras escribe
            } else {
                lblMensajeCantidad.setVisible(false); // Ocultar mensaje si está vacío
            }
        });

        // Listener para detectar selección de una opción
        CategoriaList.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                lblSeleccionaCategoria.setVisible(false); // Oculta el Label
            } else {
                lblSeleccionaCategoria.setVisible(true); // Muestra el Label si no hay selección
            }
        });

    }

    //este son alertas cuando se agrega y se actualiza el producto
    public void alertas(boolean response, int id) {
        if (response && id > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Producto");
            alert.setHeaderText(null);
            alert.setContentText("Producto Actualizado correctamente");
            alert.showAndWait();
        }
        if (response && id == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Producto");
            alert.setHeaderText(null);
            alert.setContentText("Producto agregado correctamente");
            alert.showAndWait();

        }
    }

    //valida que todos los campos sean obligatorios
    public void alertasdelFormulario() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Producto");
        alert.setHeaderText(null);
        alert.setContentText("Error todos lo campos son obligatorios");
        alert.showAndWait();

    }

    //este metodo cuando le das editar los atributos se rellenan con la informacion
    //del producto que seleccionamos
    public void atributoSeleccionadoEditado(Producto producto) {
        txtNombreProducto.setText(producto.getNombre());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        CategoriaList.getSelectionModel().select(producto.getCategoria());
    }

    //este reinicia los atributos
    public void restablecesAtributes() {
        txtNombreProducto.setText("");
        CategoriaList.getSelectionModel().clearSelection();
        txtCantidad.setText("");
        txtPrecio.setText("");
    }

    //validacion de string a decimal
    public boolean esDecimal(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //validacino de string a entero
    public boolean esEntero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //muestra el mensaje de la validacion
    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}


