package Clases;

public class Producto {
    private String id;
    private String descPro;
    private String codigoP;
    private String tipoPizza;
    private String tamañoPizza;
    private int cantidad;
    private double precio;
    private boolean activo; // Nuevo campo

    public Producto() {
    }

    public Producto(String id, String descPro, String codigoP, String tipoPizza, String tamañoPizza, int cantidad, double precio, boolean activo) {
        this.id = id;
        this.descPro = descPro;
        this.codigoP = codigoP;
        this.tipoPizza = tipoPizza;
        this.tamañoPizza = tamañoPizza;
        this.cantidad = cantidad;
        this.precio = precio;
        this.activo = activo;
    }

    // Getters y setters para todos los campos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescPro() {
        return descPro;
    }

    public void setDescPro(String descPro) {
        this.descPro = descPro;
    }

    public String getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(String codigoP) {
        this.codigoP = codigoP;
    }

    public String getTipoPizza() {
        return tipoPizza;
    }

    public void setTipoPizza(String tipoPizza) {
        this.tipoPizza = tipoPizza;
    }

    public String getTamañoPizza() {
        return tamañoPizza;
    }

    public void setTamañoPizza(String tamañoPizza) {
        this.tamañoPizza = tamañoPizza;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
