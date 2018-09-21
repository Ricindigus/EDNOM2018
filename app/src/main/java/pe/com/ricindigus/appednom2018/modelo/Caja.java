package pe.com.ricindigus.appednom2018.modelo;

public class Caja {
    private String _id;
    private String cod_barra_caja;
    private String ccdd;
    private String departamento;
    private int idnacional;
    private String idsede;
    private String nom_sede;
    private int idlocal;
    private String nom_local;
    private int tipo;
    private int nlado;
    private int acl;
    private String direccion;

    public Caja(String _id, String cod_barra_caja, String ccdd, String departamento, int idnacional, String idsede, String nom_sede, int idlocal, String nom_local, int tipo, int nlado, int acl, String direccion) {
        this._id = _id;
        this.cod_barra_caja = cod_barra_caja;
        this.ccdd = ccdd;
        this.departamento = departamento;
        this.idnacional = idnacional;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.tipo = tipo;
        this.nlado = nlado;
        this.acl = acl;
        this.direccion = direccion;
    }

    public Caja() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCod_barra_caja() {
        return cod_barra_caja;
    }

    public void setCod_barra_caja(String cod_barra_caja) {
        this.cod_barra_caja = cod_barra_caja;
    }

    public String getCcdd() {
        return ccdd;
    }

    public void setCcdd(String ccdd) {
        this.ccdd = ccdd;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getIdnacional() {
        return idnacional;
    }

    public void setIdnacional(int idnacional) {
        this.idnacional = idnacional;
    }

    public String getIdsede() {
        return idsede;
    }

    public void setIdsede(String idsede) {
        this.idsede = idsede;
    }

    public String getNom_sede() {
        return nom_sede;
    }

    public void setNom_sede(String nom_sede) {
        this.nom_sede = nom_sede;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNlado() {
        return nlado;
    }

    public void setNlado(int nlado) {
        this.nlado = nlado;
    }

    public int getAcl() {
        return acl;
    }

    public void setAcl(int acl) {
        this.acl = acl;
    }
}
