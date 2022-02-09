package jinde.appfb02.Clases;

public class Productos {

    String cantidad,
            categoria,
            iva,
            nombre,
            precio;

    public Productos(String cantidad, String categoria, String iva, String nombre, String precio) {
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.iva = iva;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + "  $" + precio+ "  -----> cant : "+cantidad ;
    }
}
