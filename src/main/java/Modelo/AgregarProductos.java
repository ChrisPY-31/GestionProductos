package Modelo;

public class AgregarProductos {
    public String nombreProducto;
    public String categoria;
    public double precio;
    public int cantidad;

    public AgregarProductos(String nombre, String categoria, double precio, int cantidad) {
        this.nombreProducto = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre(){
        return nombreProducto;
    }
    public void setnombreProducto(String nombreProducto){
        this.nombreProducto = nombreProducto;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setcategoria(String categoria){
        this.categoria = categoria;
    }
    public double getPrecio(){
        return precio;
    }
    public void setPrecio(double precio){
        this.precio = precio;
    }
    public int getCantidad(){
        return cantidad;
    }
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }


}
