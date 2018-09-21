package pe.com.ricindigus.appednom2018.modelo;

public class Inventario {
    private String id;
    private String codigo;
    private int tipo;
    private String ccdd;
    private String departamento;
    private int idnacional;
    private String idsede;
    private String nom_sede;
    private int idlocal;
    private String nom_local;
    private String dni;
    private String ape_paterno;
    private String ape_materno;
    private String nombres;
    private int naula;
    private String codpagina;
    private String direccion;

    public Inventario(){}

    public Inventario(String id, String codigo, int tipo, String ccdd, String departamento, int idnacional, String idsede, String nom_sede, int idlocal, String nom_local, String dni, String ape_paterno, String ape_materno, String nombres, int naula, String codpagina, String direccion) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.ccdd = ccdd;
        this.departamento = departamento;
        this.idnacional = idnacional;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.dni = dni;
        this.ape_paterno = ape_paterno;
        this.ape_materno = ape_materno;
        this.nombres = nombres;
        this.naula = naula;
        this.codpagina = codpagina;
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApe_paterno() {
        return ape_paterno;
    }

    public void setApe_paterno(String ape_paterno) {
        this.ape_paterno = ape_paterno;
    }

    public String getApe_materno() {
        return ape_materno;
    }

    public void setApe_materno(String ape_materno) {
        this.ape_materno = ape_materno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getNaula() {
        return naula;
    }

    public void setNaula(int naula) {
        this.naula = naula;
    }

    public String getCodpagina() {
        return codpagina;
    }

    public void setCodpagina(String codpagina) {
        this.codpagina = codpagina;
    }
}
