package pe.com.ricindigus.appednom2018.modelo;

public class Aula {
    private String _id;
    private String codigo;
    private String nro_local;
    private String naula;
    private String tipo;
    private String nombre;

    public Aula(String _id, String codigo, String nro_local, String naula, String tipo, String nombre) {
        this._id = _id;
        this.codigo = codigo;
        this.nro_local = nro_local;
        this.naula = naula;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public Aula() {
        this._id = "";
        this.codigo = "";
        this.nro_local = "";
        this.naula = "";
        this.tipo = "";
        this.nombre = "";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNro_local() {
        return nro_local;
    }

    public void setNro_local(String nro_local) {
        this.nro_local = nro_local;
    }

    public String getNaula() {
        return naula;
    }

    public void setNaula(String naula) {
        this.naula = naula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
