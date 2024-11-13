package Modelo;

import BaseDatos.ConexionPostgreSQL;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persona {
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
    private int id;

    public Persona(String nombre, String correo, String contrasena, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.id = UUID.randomUUID().hashCode();
    }

    public Persona(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String InsertarDatos() throws SQLException {
        String SQL = "INSERT INTO usuario (nombre, correo, contrasena, rol) VALUES(?,?,?,?) RETURNING rol";

        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL)) {

            pstmt.setString(1, getNombre());
            pstmt.setString(2, getCorreo());
            pstmt.setString(3, getContrasena());
            pstmt.setString(4, getRol());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("rol");
            } else {
                return "Error: No se pudo obtener el rol";
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Persona.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return "Error";
        }
    }

    public String autenticacion() {
        String query = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?";
        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("rol");
            }
            return "Error de contraseña";

        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
    }

}

