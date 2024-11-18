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

public class Pedido {
    private int id;
    private int idProducto;
    private int idUsuario;
    private String nombrePedido;
    private String categoriaPedido;
    private double precio;
    private int cantidadPedido;
    private double precioVenta;
    private double Total;

    public Pedido() {

    }

    public Pedido(int idProducto ,int idUsuario, int cantidadPedido) {
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.cantidadPedido = cantidadPedido;

    }

    public Pedido(int id ,String nombrePedido, String categoriaPedido, double precio , int cantidadPedido, double precioVenta) {
        this.id = id;
        this.nombrePedido = nombrePedido;
        this.categoriaPedido = categoriaPedido;
        this.cantidadPedido = cantidadPedido;
        this.precio = precio;
        this.precioVenta = precioVenta;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombrePedido() {
        return nombrePedido;
    }

    public void setNombrePedido(String nombrePedido) {
        this.nombrePedido = nombrePedido;
    }

    public String getCategoriaPedido() {
        return categoriaPedido;
    }

    public void setCategoriaPedido(String categoriaPedido) {
        this.categoriaPedido = categoriaPedido;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public ObservableList<Pedido> getPedidos(int idBebe) {
        ObservableList<Pedido> obs = FXCollections.observableArrayList();
        String SQL = "SELECT pedido.id_pedido, producto.nombre AS nombreProducto, producto.categoria, " +
                "producto.precio, producto.cantidad AS cantidadproducto, pedido.precioventa,idusuario " +
                "FROM pedido " +
                "INNER JOIN producto ON pedido.idproducto = producto.id " +
                "INNER JOIN usuario ON pedido.idusuario = usuario.id " +
                "WHERE pedido.idusuario =" +idBebe;

        try (Connection con = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_pedido");
                String nombre = rs.getString("nombreProducto");
                String categoria = rs.getString("categoria");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidadproducto");
                double precioVenta = rs.getDouble("precioventa");

                Pedido pedido = new Pedido(id, nombre, categoria, precio, cantidad, precioVenta);
                obs.add(pedido);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

    public boolean AgregarPedidoProducto() {
        String SQL = "INSERT INTO pedido(idproducto, idusuario, cantidad) VALUES(" +
                "" + getIdProducto() + "," +
                "" + getIdUsuario() + "," +
                "" + getCantidadPedido() +
                ")";

        try (Connection conn = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstms = conn.prepareStatement(SQL)) {

            int filas = pstms.executeUpdate();

            return filas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(Pedido.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public double CalcularTotal() {
        return Total;
    }

    public boolean EliminarPedidoProducto(int idPedido) {
        return false;
    }

}
