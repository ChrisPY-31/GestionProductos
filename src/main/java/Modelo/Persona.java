package Modelo;

import BaseDatos.ConexionPostgreSQL;

import java.sql.*;
import java.util.List;
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
    }

    public Persona(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Persona() {

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


    public String InsertarDatos() {
        String SQL = "INSERT INTO usuario (nombre, correo, contrasena, rol) VALUES (?, ?, ?, ?)";

        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, getNombre());
            pstmt.setString(2, getCorreo());
            pstmt.setString(3, getContrasena());
            pstmt.setString(4, getRol());

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                String rol;

                try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        this.id = resultSet.getInt(1);
                    }
                    rol = resultSet.getString("rol");

                }
                return rol;
            }

            return "Error";

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Pedido.class.getName());
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
                this.id = resultSet.getInt("id");
                return resultSet.getString("rol");
            }
            return "Error de contraseña";

        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
    }

    public boolean getBuscarCorreo(String correo) {
        String SQL = "SELECT correo FROM usuario WHERE correo =" + correo;

        try (Connection conn = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }


    }

    public Persona getInformationUser(int id) {
        String SQL = "SELECT * FROM usuario WHERE usuario.id =" + id;
        try (Connection conn = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                this.nombre = rs.getString("nombre");
                this.correo = rs.getString("correo");
                this.contrasena = rs.getString("contrasena");
                this.rol = rs.getString("rol");
            }
            return new Persona(nombre, correo, contrasena, rol);
        } catch (SQLException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    public boolean actualizarDatos(int id, String nombre, String contrasena) {
        StringBuilder SQL = new StringBuilder("UPDATE usuario SET ");
        boolean hasPrevious = false;

        if (nombre != null && !nombre.isEmpty()) {
            SQL.append("nombre = ?");
            hasPrevious = true;
        }

        if (contrasena != null && !contrasena.isEmpty()) {
            if (hasPrevious) {
                SQL.append(", ");
            }
            SQL.append("contrasena = ?");
        }

        SQL.append(" WHERE id = ?");

        if (!hasPrevious) {
            System.out.println("No se proporcionaron datos para actualizar.");
            return false;
        }

        try (Connection conn = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL.toString())) {

            int paramIndex = 1;
            if (nombre != null && !nombre.isEmpty()) {
                pstmt.setString(paramIndex++, nombre);
            }
            if (contrasena != null && !contrasena.isEmpty()) {
                pstmt.setString(paramIndex++, contrasena);
            }
            pstmt.setInt(paramIndex, id);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }



}

