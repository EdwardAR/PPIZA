package Clases;

public class Cliente {
    private String id;
    protected String nombre;
    protected String paterno;
    protected String materno;
    protected int Dni;
    protected int telefono;
    protected boolean activo;  // Nuevo campo

    public Cliente(String id, String nombre, String paterno, String materno, int Dni, int telefono, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.Dni = Dni;
        this.telefono = telefono;
        this.activo = activo;
    }

    public Cliente() {
    }

    // Getters y setters para todos los campos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public int getDni() {
        return Dni;
    }

    public void setDni(int Dni) {
        this.Dni = Dni;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
