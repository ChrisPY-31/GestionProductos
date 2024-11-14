package Modelo;

import BaseDatos.ConexionPostgreSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producto {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int cantidad;

    public Producto(){

    }

    public Producto(String nombre, String categoria, int cantidad, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(int id, String nombre, String categoria, int cantidad, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean AgregarProducto() {
        String SQL = "INSERT INTO Producto (nombre, categoria, cantidad, precio) VALUES(?,?,?,?)";

        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, categoria);
            pstmt.setInt(3, cantidad);
            pstmt.setDouble(4, precio);

            int filas = pstmt.executeUpdate();  // Ejecuta el INSERT
            return filas > 0;

        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(Producto.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }


    public void ActualizarProducto() {
    }

    public boolean EliminarProducto(int IDProducto) {
        return false;
    }

    public void BuscarProducto() {
    }

    public ObservableList<Producto> getProductos() {
        ObservableList<Producto> obs = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM producto";
        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                Producto producto = new Producto(id, nombre, categoria, cantidad, precio);
                obs.add(producto);
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(Producto.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
        return obs;
    }

}
