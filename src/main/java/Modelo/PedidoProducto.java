package Modelo;

public class PedidoProducto {
    private String idPedido;
    private String idProducto;
    private String idUsuario;
    private int cantidadProductos;
    private double precio;
    private double Total;

    public String getidPedido() {
        return idPedido;
    }
    public void setidPedido() {
        this.idPedido = idPedido;
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



}
