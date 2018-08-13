package pe.com.ricindigus.appednom2018.modelo;

public class UsuarioLocal {
    private String _id;
    private String usuario;
    private String clave;
    private String rol;
    private String nro_local;
    private String nombreLocal;
    private String naulas;
    private String ncontingencia;
    private String codsede;
    private String sede;
    private String nombre;


    public UsuarioLocal(String _id, String usuario, String clave, String rol, String nro_local, String nombreLocal, String naulas, String ncontingencia, String codsede, String sede, String nombre) {
        this._id = _id;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
        this.nro_local = nro_local;
        this.nombreLocal = nombreLocal;
        this.naulas = naulas;
        this.ncontingencia = ncontingencia;
        this.codsede = codsede;
        this.sede = sede;
        this.nombre = nombre;
    }

    public UsuarioLocal() {
        this._id = "";
        this.usuario = "";
        this.clave = "";
        this.rol = "";
        this.nro_local = "";
        this.nombreLocal = "";
        this.naulas = "";
        this.ncontingencia = "";
        this.codsede = "";
        this.sede = "";
        this.nombre = "";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNro_local() {
        return nro_local;
    }

    public void setNro_local(String nro_local) {
        this.nro_local = nro_local;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getNaulas() {
        return naulas;
    }

    public void setNaulas(String naulas) {
        this.naulas = naulas;
    }

    public String getNcontingencia() {
        return ncontingencia;
    }

    public void setNcontingencia(String ncontingencia) {
        this.ncontingencia = ncontingencia;
    }

    public String getCodsede() {
        return codsede;
    }

    public void setCodsede(String codsede) {
        this.codsede = codsede;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
