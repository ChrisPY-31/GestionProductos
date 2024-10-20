package Modelo;

public class Producto {
    private String IDproducto;
    public String nombreProducto;
    protected String categoria;
    private double precioCompra;
    public double precioVenta;
    protected int cantidadStock;

    public Producto(String IDproducto, String nombreProducto, String categoria, double precioCompra, double precioVenta) {
        this.IDproducto = IDproducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidadStock = 0;
    }

    public String getIDproducto(){
        return IDproducto;
    }


}
