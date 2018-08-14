package pe.com.ricindigus.appednom2018.modelo;

public class UsuarioLocal {
    private int _id;
    private String usuario;
    private String clave;
    private int rol;
    private int nro_local;
    private String nombreLocal;
    private int naulas;
    private int ncontingencia;
    private int codsede;
    private String sede;
    private String nombre;


    public UsuarioLocal(int _id, String usuario, String clave, int rol, int nro_local, String nombreLocal, int naulas, int ncontingencia, int codsede, String sede, String nombre) {
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
        this._id = 0;
        this.usuario = "";
        this.clave = "";
        this.rol = 0;
        this.nro_local = 0;
        this.nombreLocal = "";
        this.naulas = 0;
        this.ncontingencia = 0;
        this.codsede = 0;
        this.sede = "";
        this.nombre = "";
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getNro_local() {
        return nro_local;
    }

    public void setNro_local(int nro_local) {
        this.nro_local = nro_local;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public int getNaulas() {
        return naulas;
    }

    public void setNaulas(int naulas) {
        this.naulas = naulas;
    }

    public int getNcontingencia() {
        return ncontingencia;
    }

    public void setNcontingencia(int ncontingencia) {
        this.ncontingencia = ncontingencia;
    }

    public int getCodsede() {
        return codsede;
    }

    public void setCodsede(int codsede) {
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
