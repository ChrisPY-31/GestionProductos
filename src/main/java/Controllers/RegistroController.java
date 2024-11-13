package Controllers;

import Modelo.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroController implements Initializable {

    @FXML
    private PasswordField txtContraseñaUsuario;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtTelefonoUsuario;

    @FXML
    private ComboBox<String> RolList;


    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,}$";

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @FXML
    void btnIniciaSesion() {
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

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void btnRegistrarUsuario(ActionEvent event) throws SQLException {
        String nombreUsuario = txtNombreUsuario.getText();
        String correoUsuario = txtCorreoUsuario.getText();
        String contrasenaUsuario = txtContraseñaUsuario.getText();
        String rol = RolList.getSelectionModel().getSelectedItem();


        //if(!validateEmail(correoUsuario) || !validatePassword(contrasenaUsuario)) {
          //  alertas("Debe tener un caracter especial");
        //}

        Persona persona = new Persona(nombreUsuario, correoUsuario, contrasenaUsuario, rol);


        String response = persona.InsertarDatos();
        System.out.println(response);
        //String registro = response ? "Se Registro correctamente" : "No se registro";
        //alertas(registro);
        //if (response) {
          //  navegacinoUsuario();
        //limpiarFormulario();
        // } else {
        //   alertas(registro);
        // }


    }

    public void navegacinoUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsUser());

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void alertas(String mensaje) {
        if (mensaje.equals("Se Registro correctamente")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
    }

    public void limpiarFormulario() {
        txtCorreoUsuario.clear();
        txtNombreUsuario.clear();
    }

    public void closeWindowsRegistro() {
        try {

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void ElegirRol(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Administrador", "usuario");
        RolList.setItems(list);
    }

}
