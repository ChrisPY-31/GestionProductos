package Modelo;

public class Producto {
    public String id;
    public String nombreProducto;
    public String categoria;
    public double precio;
    public int cantidad;

    public Producto(String nombre, String categoria, double precio, int cantidad) {
        this.nombreProducto = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombreProducto;
    }

    public void setnombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setcategoria(String categoria) {
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

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void AgregarProducto(){}

    public void ActualizarProducto(){}

    public boolean EliminarProducto(int IDProducto){
        return false;
    }

    public void BuscarProducto(){}


}
