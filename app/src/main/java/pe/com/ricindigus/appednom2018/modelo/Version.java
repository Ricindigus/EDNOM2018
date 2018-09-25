package pe.com.ricindigus.appednom2018.modelo;

public class Version {
    private int id;
    private int numero;
    private String nombre;
    private String coleccion_cajas;
    private String coleccion_inventario;
    private String coleccion_asistencia;
    private int asis_hora_inicio;
    private int asis_hora_fin;
    private int asis_min_inicio;
    private int asis_min_fin;

    public Version() {
    }

    public Version(int id, int numero, String nombre, String coleccion_cajas, String coleccion_inventario, String coleccion_asistencia, int asis_hora_inicio, int asis_hora_fin, int asis_min_inicio, int asis_min_fin) {
        this.id = id;
        this.numero = numero;
        this.nombre = nombre;
        this.coleccion_cajas = coleccion_cajas;
        this.coleccion_inventario = coleccion_inventario;
        this.coleccion_asistencia = coleccion_asistencia;
        this.asis_hora_inicio = asis_hora_inicio;
        this.asis_hora_fin = asis_hora_fin;
        this.asis_min_inicio = asis_min_inicio;
        this.asis_min_fin = asis_min_fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColeccion_cajas() {
        return coleccion_cajas;
    }

    public void setColeccion_cajas(String coleccion_cajas) {
        this.coleccion_cajas = coleccion_cajas;
    }

    public String getColeccion_inventario() {
        return coleccion_inventario;
    }

    public void setColeccion_inventario(String coleccion_inventario) {
        this.coleccion_inventario = coleccion_inventario;
    }

    public String getColeccion_asistencia() {
        return coleccion_asistencia;
    }

    public void setColeccion_asistencia(String coleccion_asistencia) {
        this.coleccion_asistencia = coleccion_asistencia;
    }

    public int getAsis_hora_inicio() {
        return asis_hora_inicio;
    }

    public void setAsis_hora_inicio(int asis_hora_inicio) {
        this.asis_hora_inicio = asis_hora_inicio;
    }

    public int getAsis_hora_fin() {
        return asis_hora_fin;
    }

    public void setAsis_hora_fin(int asis_hora_fin) {
        this.asis_hora_fin = asis_hora_fin;
    }

    public int getAsis_min_inicio() {
        return asis_min_inicio;
    }

    public void setAsis_min_inicio(int asis_min_inicio) {
        this.asis_min_inicio = asis_min_inicio;
    }

    public int getAsis_min_fin() {
        return asis_min_fin;
    }

    public void setAsis_min_fin(int asis_min_fin) {
        this.asis_min_fin = asis_min_fin;
    }
}
