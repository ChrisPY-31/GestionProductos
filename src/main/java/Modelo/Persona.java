package Modelo;

import BaseDatos.ConexionPostgreSQL;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persona {
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
    private String IDUsuario;

    public Persona(String nombre, String correo, String contrasena, String rol, String IDUsuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.IDUsuario = IDUsuario;
    }

    public Persona(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Persona() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIDUsuario() {
        return IDUsuario;
    }
    public void setIDUsuario(String IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public boolean InsertarDatos() throws SQLException {
        String SQL = "INSERT INTO usuarios (nombre, correo, contrasena, rol, telefono) VALUES(?,?,?,?,?)";

        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL)) {

            // Establece los valores para los parámetros
            pstmt.setString(1, getNombre());
            pstmt.setString(2, getCorreo());
            pstmt.setString(3, getContrasena());
            pstmt.setString(4, getRol());

            pstmt.executeUpdate();
            System.out.println("Successfully created.");
            return true;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Persona.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }

    }

    public boolean autenticacion() {
        String query = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);

            ResultSet resultSet = pstmt.executeQuery();

            //this.rolBD = resultSet.getString("rol");
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
