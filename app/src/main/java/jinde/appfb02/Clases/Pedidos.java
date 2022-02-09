package jinde.appfb02.Clases;

public class Pedidos {
    String id, correo, nombre, apellido, direccion, totalparcial;
    double total;

    public Pedidos(String id, String correo, String nombre, String apellido, String direccion, String totalparcial, double total) {
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.totalparcial = totalparcial;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTotalparcial() {
        return totalparcial;
    }

    public void setTotalparcial(String totalparcial) {
        this.totalparcial = totalparcial;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido+"     $"+total;
    }
}
