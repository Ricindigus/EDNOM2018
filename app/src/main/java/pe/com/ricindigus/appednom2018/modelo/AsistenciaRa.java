package pe.com.ricindigus.appednom2018.modelo;

public class AsistenciaRa {
    private int id;
    private String ccdd;
    private String departamento;
    private String idsede;
    private String nom_sede;
    private int idnacional;
    private int idlocal;
    private String nom_local;
    private int red;
    private int tipo_cargo;
    private String nombre_cargo;
    private String dni;
    private String nombres_completos;

    public AsistenciaRa(int id, String ccdd, String departamento, String idsede, String nom_sede, int idnacional, int idlocal, String nom_local, int red, int tipo_cargo, String nombre_cargo, String dni, String nombres_completos) {
        this.id = id;
        this.ccdd = ccdd;
        this.departamento = departamento;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
        this.idnacional = idnacional;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.red = red;
        this.tipo_cargo = tipo_cargo;
        this.nombre_cargo = nombre_cargo;
        this.dni = dni;
        this.nombres_completos = nombres_completos;
    }

    public AsistenciaRa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdnacional() {
        return idnacional;
    }

    public void setIdnacional(int idnacional) {
        this.idnacional = idnacional;
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

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getTipo_cargo() {
        return tipo_cargo;
    }

    public void setTipo_cargo(int tipo_cargo) {
        this.tipo_cargo = tipo_cargo;
    }

    public String getNombre_cargo() {
        return nombre_cargo;
    }

    public void setNombre_cargo(String nombre_cargo) {
        this.nombre_cargo = nombre_cargo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres_completos() {
        return nombres_completos;
    }

    public void setNombres_completos(String nombres_completos) {
        this.nombres_completos = nombres_completos;
    }
}
