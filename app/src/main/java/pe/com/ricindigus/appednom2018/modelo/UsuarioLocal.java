package pe.com.ricindigus.appednom2018.modelo;

public class UsuarioLocal {
    private int _id;
    private String usuario;
    private String clave;
    private int rol;
    private int idlocal;
    private String nom_local;
    private int naulas;
    private int idsede;
    private String nom_sede;


    public UsuarioLocal(int _id, String usuario, String clave, int rol, int idlocal, String nom_local, int naulas, int idsede, String nom_sede) {
        this._id = _id;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.naulas = naulas;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
    }

    public UsuarioLocal() {
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

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    public String getNom_local() {
        return nom_local;
    }

    public void setNom_local(String nom_local) {
        this.nom_local = nom_local;
    }

    public int getNaulas() {
        return naulas;
    }

    public void setNaulas(int naulas) {
        this.naulas = naulas;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public String getNom_sede() {
        return nom_sede;
    }

    public void setNom_sede(String nom_sede) {
        this.nom_sede = nom_sede;
    }
}
