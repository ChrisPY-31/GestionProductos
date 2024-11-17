package Modelo;

import BaseDatos.ConexionPostgreSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pedido {
    private String id;
    private String idProducto;
    private String idUsuario;
    private int cantidadProductos;
    private double precio;
    private double Total;

    public Pedido(){

    }

    public Pedido(String idProducto, String idUsuario, int cantidadProductos, double precio, double total) {
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.cantidadProductos = cantidadProductos;
        this.precio = precio;
        Total = total;
    }

    public String getidPedido() {
        return id;
    }

    public void setidPedido() {
        this.id = id;
    }

    public String getidProducto() {
        return idProducto;
    }

    public void setidProducto() {
        this.idProducto = idProducto;
    }

    public String getidUsuario() {
        return idUsuario;
    }

    public void setidUsuario() {
        this.idUsuario = idUsuario;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos() {
        this.cantidadProductos = cantidadProductos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio() {
        this.precio = precio;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal() {
        this.Total = Total;
    }


    public void AgregarPedidoProducto() {

    }

    public double CalcularTotal(){
        return Total;
    }

    public boolean EliminarPedidoProducto(int idPedido) {
        return false;
    }

}
